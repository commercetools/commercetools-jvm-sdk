package io.sphere.sdk.test;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import io.sphere.sdk.client.*;
import io.sphere.sdk.http.AsyncHttpClientAdapter;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.queries.Query;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public abstract class IntegrationTest {

    private static volatile int threadCountAtStart;
    private static TestClient client;

    protected synchronized static TestClient client() {
        if (client == null) {
            final SphereClientConfig config = getSphereClientConfig();
            final HttpClient httpClient = newHttpClient();
            final SphereAccessTokenSupplier tokenSupplier = SphereAccessTokenSupplier.ofAutoRefresh(config, httpClient, false);
            final SphereClient underlying = SphereClient.of(config, httpClient, tokenSupplier);
            client = new TestClient(withMaybeDeprecationWarnTool(underlying));
        }
        return client;
    }

    protected static SphereClient sphereClient() {
        return client().getUnderlying();
    }

    private static SphereClient withMaybeDeprecationWarnTool(final SphereClient underlying) {
        if ("false".equals(System.getenv("JVM_SDK_IT_DEPRECATION"))) {
            LoggerFactory.getLogger(IntegrationTest.class).info("Deprecation client deactivated.");
            return underlying;
        } else {
            return DeprecationExceptionSphereClientDecorator.of(underlying);
        }
    }

    protected static HttpClient newHttpClient() {
        final AsyncHttpClient asyncHttpClient = new AsyncHttpClient(new AsyncHttpClientConfig.Builder().setAcceptAnyCertificate(true).build());
        return AsyncHttpClientAdapter.of(asyncHttpClient);
    }

    public static SphereClientConfig getSphereClientConfig() {
        return SphereClientConfig.ofEnvironmentVariables("JVM_SDK_IT");
    }

    protected static <T> T execute(final SphereRequest<T> sphereRequest) {
        try {
            return client().execute(sphereRequest);
        } catch (final TestClientException e) {
            if (e.getCause() != null && e.getCause() instanceof RuntimeException) {
                throw (RuntimeException) e.getCause();
            } else {
                throw e;
            }
        }
    }

    @BeforeClass
    public synchronized static void setup() {
        threadCountAtStart = countThreads();
    }

    @AfterClass
    public synchronized static void shutdownClient() {
        if (client != null) {
            client.close();
            client = null;
            final int threadsNow = countThreads();
            /*
            The buffer needs to be high since SBT runs tests for multiple projects in parallel
             */
            final long bufferForGcThreadAndSbt = 40;
            final long allowedThreadCount = threadCountAtStart + bufferForGcThreadAndSbt;
            if (threadsNow > allowedThreadCount) {
                throw new RuntimeException("Thread leak! After client shutdown created threads are still alive. Threads now: " + threadsNow + " Threads before: " + threadCountAtStart);
            }
        }
    }

    protected static <T> T getOrCreate(final SphereRequest<T> createCommand, final Query<T> query) {
        return execute(query).head().orElseGet(() -> execute(createCommand));
    }

    protected static int countThreads() {
        return Thread.activeCount();
    }
}
