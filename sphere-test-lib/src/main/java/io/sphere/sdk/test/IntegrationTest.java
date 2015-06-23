package io.sphere.sdk.test;

import io.sphere.sdk.client.*;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.queries.Query;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.Optional;

public abstract class IntegrationTest {

    private static volatile int threadCountAtStart;
    private static TestClient client;

    protected synchronized static TestClient client() {
        if (client == null) {
            final SphereClientFactory factory = SphereClientFactory.of();
            final SphereClientConfig config = getSphereClientConfig();
            final SphereAccessTokenSupplier tokenSupplier = SphereAccessTokenSupplierFactory.of().createSupplierOfOneTimeFetchingToken(config);
            final SphereClient underlying = factory.createClient(config, tokenSupplier);
            final SphereClient underlyingWithDeprecationExceptions = DeprecationExceptionSphereClientDecorator.of(underlying);
            client = new TestClient(underlyingWithDeprecationExceptions);
        }
        return client;
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
            final long bufferForGcThreadAndSbt = 7;
            final long allowedThreadCount = threadCountAtStart + bufferForGcThreadAndSbt;
            if (threadsNow > allowedThreadCount) {
                throw new RuntimeException("Thread leak! After client shutdown created threads are still alive. Threads now: " + threadsNow + " Threads before: " + threadCountAtStart);
            }
        }
    }

    protected static <T> T getOrCreate(final SphereRequest<T> createCommand, final Query<T> query) {
        final Optional<T> resultOption = execute(query).head();
        return resultOption.orElseGet(() -> execute(createCommand));
    }

    protected static int countThreads() {
        return Thread.activeCount();
    }
}
