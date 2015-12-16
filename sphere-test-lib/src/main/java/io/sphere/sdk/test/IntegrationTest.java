package io.sphere.sdk.test;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import io.sphere.sdk.client.*;
import io.sphere.sdk.http.AsyncHttpClientAdapter;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.models.DefaultCurrencyUnits;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import org.assertj.core.api.Condition;
import org.assertj.core.api.SoftAssertions;
import org.junit.BeforeClass;
import org.slf4j.LoggerFactory;

import javax.money.CurrencyUnit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public abstract class IntegrationTest {

    private static BlockingSphereClient client;

    @BeforeClass
    public static void warmUpJavaMoney() throws Exception {
        final CurrencyUnit eur = DefaultCurrencyUnits.EUR;//workaround for https://github.com/sphereio/sphere-jvm-sdk/issues/779
    }

    public static void setupClient() {
        if (client == null) {
            final SphereClientConfig config = getSphereClientConfig();
            final HttpClient httpClient = newHttpClient();
            final SphereAccessTokenSupplier tokenSupplier = SphereAccessTokenSupplier.ofAutoRefresh(config, httpClient, false);
            final SphereClient underlying = SphereClient.of(config, httpClient, tokenSupplier);
            final SphereClient underlying1 = withMaybeDeprecationWarnTool(underlying);
            client = BlockingSphereClient.of(underlying1, 20, TimeUnit.SECONDS);
        }
    }

    protected synchronized static BlockingSphereClient client() {
        setupClient();
        return client;
    }

    protected static SphereClient sphereClient() {
        return client();
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

    protected static String accessToken() {
        final SphereAccessTokenSupplier sphereAccessTokenSupplier = SphereAccessTokenSupplier.ofOneTimeFetchingToken(getSphereClientConfig(), newHttpClient(), true);
        final String accessToken = sphereAccessTokenSupplier.get().toCompletableFuture().join();
        sphereAccessTokenSupplier.close();
        return accessToken;
    }

    public static SphereClientConfig getSphereClientConfig() {
        final File file = new File("integrationtest.properties");
        return file.exists() ? loadViaProperties(file) : loadViaEnvironmentArgs();
    }

    private static SphereClientConfig loadViaEnvironmentArgs() {
        return SphereClientConfig.ofEnvironmentVariables("JVM_SDK_IT");
    }

    private static SphereClientConfig loadViaProperties(final File file) {
        try (final FileInputStream fileInputStream = new FileInputStream(file)) {
            final Properties properties = new Properties();
            properties.load(fileInputStream);
            return SphereClientConfig.ofProperties(properties, "");
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    protected static <T> T execute(final SphereRequest<T> sphereRequest) {
        try {
            return client().executeBlocking(sphereRequest);
        } catch (final TestClientException e) {
            if (e.getCause() != null && e.getCause() instanceof RuntimeException) {
                throw (RuntimeException) e.getCause();
            } else {
                throw e;
            }
        }
    }

    public synchronized static void shutdownClient() {
        if (client != null) {
            client.close();
            client = null;
        }
    }

    protected static <T> T getOrCreate(final SphereRequest<T> createCommand, final Query<T> query) {
        return execute(query).head().orElseGet(() -> execute(createCommand));
    }

    protected static <T> Condition<PagedQueryResult<T>> onlyTheResult(final T expected) {
        return new Condition<PagedQueryResult<T>>("contains only the result " + expected) {
            @Override
            public boolean matches(final PagedQueryResult<T> value) {
                return value.getResults().size() == 1 && value.getResults().get(0).equals(expected);
            }
        };
    }

    protected static <T extends Reference<?>> Condition<T> expanded() {
        return new Condition<T>("is expanded") {
            @Override
            public boolean matches(final T value) {
                return value.getObj() != null;
            }
        };
    }

    protected static <A, T extends Reference<A>> Condition<T> expanded(final A expected) {
        return new Condition<T>("is expanded as " + expected) {
            @Override
            public boolean matches(final T value) {
                return value.getObj() != null && value.getObj().equals(expected);
            }
        };
    }

    protected static void softAssert(final Consumer<SoftAssertions> assertionsConsumer) {
        final SoftAssertions softly = new SoftAssertions();
        assertionsConsumer.accept(softly);
        softly.assertAll();
    }
}
