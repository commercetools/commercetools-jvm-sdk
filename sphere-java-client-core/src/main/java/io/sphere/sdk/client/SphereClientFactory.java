package io.sphere.sdk.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.SphereException;
import io.sphere.sdk.utils.CompletableFutureUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.sphere.sdk.utils.CompletableFutureUtils.successful;

/**
 * A factory to instantiate SPHERE.IO Java clients which use {@link CompletionStage} as future implementation.
 *
 * {@include.example example.JavaClientInstantiationExample}
 */
public class SphereClientFactory extends Base {
    private final Supplier<HttpClient> httpClientSupplier;

    private SphereClientFactory(final Supplier<HttpClient> httpClientSupplier) {
        this.httpClientSupplier = httpClientSupplier;
    }

    public static SphereClientFactory of(final Supplier<HttpClient> httpClientSupplier) {
        return new SphereClientFactory(httpClientSupplier);
    }

    public static SphereClientFactory of() {
        try {
            Class<?> clazz;
            try {
                clazz = Class.forName("io.sphere.sdk.client.SphereAsyncHttpClientFactory");
            } catch (final ClassNotFoundException e) {
                clazz = Class.forName("io.sphere.sdk.client.SphereApacheHttpClientFactory");
            }
            final Method create = clazz.getMethod("create");
            final Supplier<HttpClient> supplier = () -> {
                try {
                    return  (HttpClient) create.invoke(null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new SphereException(e);
                }
            };
            return of(supplier);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new SphereException(e);
        }
    }

    private HttpClient defaultHttpClient() {
        return httpClientSupplier.get();
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
     * deprecated
     * @deprecated use {@link TestDoubleSphereClientFactory#createHttpTestDouble(Function)}
     * @param function mapping from intent to http response
     * @return test double
     */
    @Deprecated
    public static SphereClient createHttpTestDouble(final Function<HttpRequestIntent, HttpResponse> function) {
        return TestDoubleSphereClientFactory.createHttpTestDouble(function);
    }

    /**
     * deprecated
     * @deprecated use {@link TestDoubleSphereClientFactory#createObjectTestDouble(Function)}
     * @param function a function which returns a matching http request for a SPHERE.IO request.
     * @return sphere client test double
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    public static SphereClient createObjectTestDouble(final Function<HttpRequestIntent, Object> function) {
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
