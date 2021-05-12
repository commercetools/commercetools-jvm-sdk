package io.sphere.sdk.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.sphere.sdk.client.*;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.DefaultCurrencyUnits;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;

import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.client5.http.ssl.DefaultClientTlsStrategy;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.core5.http.config.Lookup;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.nio.ssl.TlsStrategy;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.assertj.core.api.Condition;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Modifier;
import java.nio.file.Paths;
import java.security.cert.X509Certificate;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static io.sphere.sdk.utils.SphereInternalUtils.listOf;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class IntegrationTest {

    private static final int MAX_DEPTH_LEVEL = 3;

    @Rule
    public Timeout globalTimeout = Timeout.seconds(1500);

    protected static final Logger logger = LoggerFactory.getLogger(IntegrationTest.class);

    private static BlockingSphereClient client;

    @Before
    public void classNameCheck() {
        assertThat(getClass().getSimpleName()).endsWith("IntegrationTest");
        assertThat(Modifier.isFinal(getClass().getModifiers()))
                .as(String.format("Class '%s' should not be final since it might be subject to extension for OSGi test",getClass().getName()))
                .isFalse();
    }

    @BeforeClass
    public static void warmUpJavaMoney() throws Exception {
        final CurrencyUnit eur = DefaultCurrencyUnits.EUR;//workaround for https://github.com/commercetools/commercetools-jvm-sdk/issues/779
    }

    public static void setupClient() {
        if (client == null) {
            final SphereClientConfig config = getSphereClientConfig();
            final HttpClient httpClient = newHttpClient();
            final SphereAccessTokenSupplier tokenSupplier = SphereAccessTokenSupplier.ofAutoRefresh(config, httpClient, false);
            final SphereClient underlying = SphereClient.of(config, httpClient, tokenSupplier);
            final SphereClient underlying1 = withMaybeDeprecationWarnTool(underlying);
            client = BlockingSphereClient.of(underlying1, 30, TimeUnit.SECONDS);
            assertProjectSettingsAreFine(client);
        }
    }
//
//    @AfterClass
//    public static void closeClient() {
//        if (client != null) {
//            client.close();
//        }
//    }

    public static void assertProjectSettingsAreFine(final BlockingSphereClient sphereClient) {
        final JsonNode project = sphereClient.executeBlocking(new SphereRequest<JsonNode>() {
            @Nullable
            @Override
            public JsonNode deserialize(final HttpResponse httpResponse) {
                return SphereJsonUtils.parse(httpResponse.getResponseBody());
            }

            @Override
            public HttpRequestIntent httpRequestIntent() {
                return HttpRequestIntent.of(HttpMethod.GET, "/");
            }
        });

        final ArrayNode languagesArray = (ArrayNode) project.get("languages");
        final List<String> languages = new LinkedList<>();
        languagesArray.elements().forEachRemaining(jsonNode -> languages.add(jsonNode.asText()));
        assertThat(languages).as("tests will fail if the project has not the correct language settings").contains("de", "de-AT", "en");
    }

    protected synchronized static BlockingSphereClient client() {
        setupClient();
        return client;
    }

    protected static SphereClient sphereClient() {
        return client();
    }

    private static SphereClient withMaybeDeprecationWarnTool(final SphereClient underlying) {
        //deprecation was disabled for RC1
        return underlying;
    }

    protected static HttpClient newHttpClient() {
        //NO SSL Client: this client doesn't perform ssl certification check, which is a necessity to run tests on CI
        CloseableHttpAsyncClient asyncClient = createNoSSLClient();
        return IntegrationTestHttpClient.of(asyncClient);
    }

    private static CloseableHttpAsyncClient createNoSSLClient() {
        final TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) ->  true;
        try {
            final SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            Lookup<TlsStrategy> socketFactoryRegistry = RegistryBuilder.<TlsStrategy>create()
                   .register("https", new DefaultClientTlsStrategy(sslContext, NoopHostnameVerifier.INSTANCE))
                   .build();
            PoolingAsyncClientConnectionManager connManager = new PoolingAsyncClientConnectionManager(
                    socketFactoryRegistry);
            return HttpAsyncClients.createMinimal(connManager);
        } catch (Exception e) {
            logger.error("Could not create SSLContext",e);
            return null;
        }
    }

    protected static String accessToken() {
        final SphereAccessTokenSupplier sphereAccessTokenSupplier = SphereAccessTokenSupplier.ofOneTimeFetchingToken(getSphereClientConfig(), newHttpClient(), true);
        final String accessToken = sphereAccessTokenSupplier.get().toCompletableFuture().join();
        sphereAccessTokenSupplier.close();
        return accessToken;
    }

    public static SphereClientConfig getSphereClientConfig() {

        String propertiesFile = "integrationtest.properties";
        String parentDir = ".";
        for (int i = 0; i < MAX_DEPTH_LEVEL; i++) {
            if (Paths.get(parentDir, propertiesFile).toFile().exists()) {
                return loadViaProperties(Paths.get(parentDir, propertiesFile).toFile());
            }
            parentDir = "../" + parentDir;
        }
        return loadViaEnvironmentArgs();
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

    public synchronized static void shutdownClient() {
        if (client != null) {
            client.close();
            client = null;
        }
    }

    protected static <T> T getOrCreate(final SphereRequest<T> createCommand, final Query<T> query) {
        return client().executeBlocking(query).head().orElseGet(() -> client().executeBlocking(createCommand));
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
        return new Condition<T>("expanded") {
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


    protected static void await(final CompletionStage<?> wait1, final CompletionStage<?> ... moreWait) {
        listOf(wait1, moreWait).forEach(stage -> stage.toCompletableFuture().join());
    }
}
