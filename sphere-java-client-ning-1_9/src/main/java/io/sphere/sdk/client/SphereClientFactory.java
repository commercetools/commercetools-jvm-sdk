package io.sphere.sdk.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.http.NingHttpClientAdapter;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.json.JsonUtils;
import io.sphere.sdk.utils.CompletableFutureUtils;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import static io.sphere.sdk.utils.CompletableFutureUtils.*;

/**
 * A factory to instantiate SPHERE.IO Java clients which use {@link java.util.concurrent.CompletionStage} as future implementation.
 *
 * {@include.example example.JavaClientInstantiationExample}
 */
public class SphereClientFactory extends Base {
    private SphereClientFactory() {
    }

    private HttpClient defaultHttpClient() {
        return NingHttpClientAdapter.of();
    }

    /**
     * Creates a standard client with configurable service URLs. Intended for commercetools staff
     * developing with a custom SPHERE.IO instance.
     *
     * @param config configuration for the client
     * @return client
     */
    public SphereClient createClient(final SphereClientConfig config) {
        final HttpClient httpClient = defaultHttpClient();
        final SphereAccessTokenSupplier tokenSupplier = SphereAccessTokenSupplier.ofAutoRefresh(config, httpClient, false);
        return SphereClient.of(config, httpClient, tokenSupplier);
    }

    public static SphereClientFactory of() {
        return new SphereClientFactory();
    }

    /**
     * Creates a client with a custom service to provide access tokens.
     * @param config the configuration to use the API
     * @param tokenSupplier a service which provides tokens
     * @return client
     */
    public SphereClient createClient(final SphereApiConfig config, final SphereAccessTokenSupplier tokenSupplier) {
        return SphereClient.of(config, defaultHttpClient(), tokenSupplier);
    }

    /**
     * Creates a standard client suitable for online shops.
     *
     * For the credentials consult <a href="https://admin.sphere.io">the Merchant Center</a>.
     * @param projectKey the project identifier
     * @param clientId username
     * @param clientSecret password
     * @return sphere client
     */
    public SphereClient createClient(final String projectKey, final String clientId, final String clientSecret) {
        return createClient(SphereClientConfig.of(projectKey, clientId, clientSecret));
    }

    /**
     * Creates a test double for a SPHERE.IO client which enables to fake http responses from SPHERE.IO.
     * The client does not need an internet connection.
     *
     * {@include.example io.sphere.sdk.client.TestsDemo#withJson()}
     *
     * @param function a function which returns a matching object for a SPHERE.IO request.
     * @return sphere client test double
     */
    public SphereClient createHttpTestDouble(final Function<HttpRequestIntent, HttpResponse> function) {
        return new SphereClient() {
            private final ObjectMapper objectMapper = JsonUtils.newObjectMapper();

            @Override
            public <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest) {
                final HttpRequestIntent httpRequest = sphereRequest.httpRequestIntent();
                final HttpResponse httpResponse = function.apply(httpRequest);
                try {
                    final T t = SphereClientImpl.parse(sphereRequest, objectMapper, SphereApiConfig.of("fake-project-key-for-testing", "https://createHttpTestDouble.tld"), httpResponse);
                    return CompletableFutureUtils.successful(t);
                } catch (final Exception e) {
                    return CompletableFutureUtils.failed(e);
                }
            }

            @Override
            public void close() {
            }

            @Override
            public String toString() {
                return "SphereClientHttpTestDouble";
            }
        };
    }

    /**
     * Creates a test double for a SPHERE.IO client which enables to fake the results of the client as Java object.
     * The client does not need an internet connection.
     *
     * {@include.example io.sphere.sdk.client.TestsDemo#withInstanceResults()}
     * {@include.example io.sphere.sdk.client.TestsDemo#modelInstanceFromJson()}
     *
     * @param function a function which returns a matching http request for a SPHERE.IO request.
     * @return sphere client test double
     */
    @SuppressWarnings("unchecked")
    public SphereClient createObjectTestDouble(final Function<HttpRequestIntent, Object> function) {
        return new SphereClient() {
            @Override
            public <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest) {
                final T result = (T) function.apply(sphereRequest.httpRequestIntent());
                return successful(result);
            }

            @Override
            public void close() {
            }

            @Override
            public String toString() {
                return "SphereClientObjectTestDouble";
            }
        };
    }
}
