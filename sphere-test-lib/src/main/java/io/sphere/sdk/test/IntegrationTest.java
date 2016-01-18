package io.sphere.sdk.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import io.sphere.sdk.client.*;
import io.sphere.sdk.http.AsyncHttpClientAdapter;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.DefaultCurrencyUnits;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import org.assertj.core.api.Condition;
import org.assertj.core.api.SoftAssertions;
import org.junit.BeforeClass;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

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
            assertProjectSettingsAreFine(client);
        }
    }

    private static void assertProjectSettingsAreFine(final BlockingSphereClient sphereClient) {
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
        if ("true".equals(System.getenv("JVM_SDK_IT_DEPRECATION"))) {
            return DeprecationExceptionSphereClientDecorator.of(underlying);
        } else {
            LoggerFactory.getLogger(IntegrationTest.class).info("Deprecation client deactivated.");
            return underlying;
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
