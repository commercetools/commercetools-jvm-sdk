package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.SphereException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ServiceLoader;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

/**
 * A factory to instantiate platform Java clients which use {@link CompletionStage} as future implementation.
 *
 * {@include.example example.JavaClientInstantiationExample}
 */
public interface SphereClientFactory {
    HttpClient createHttpClient();

    /**
     * Creates a standard client with configurable service URLs. Intended for commercetools staff
     * developing with a custom platform instance.
     *
     * @param config configuration for the client
     * @return client
     */
    default SphereClient createClient(SphereClientConfig config) {
        final HttpClient httpClient = createHttpClient();
        final SphereAccessTokenSupplier tokenSupplier = SphereAccessTokenSupplier.ofAutoRefresh(config, httpClient, false);
        return SphereClient.of(config, httpClient, tokenSupplier);
    }

    /**
     * Creates a client with a custom service to provide access tokens.
     * @param config the configuration to use the API
     * @param tokenSupplier a service which provides tokens
     * @return client
     */
    default SphereClient createClient(SphereApiConfig config, SphereAccessTokenSupplier tokenSupplier) {
        return SphereClient.of(config, createHttpClient(), tokenSupplier);
    }

    /**
     * Creates a standard client suitable for online shops.
     *
     * For the credentials consult <a href="https://mc.commercetools.com">the Merchant Center</a>.
     * @param projectKey the project identifier
     * @param clientId username
     * @param clientSecret password
     * @return sphere client
     */
    default SphereClient createClient(String projectKey, String clientId, String clientSecret) {
        return createClient(SphereClientConfig.of(projectKey, clientId, clientSecret));
    }


    /**
     * Creates a standard client suitable for online shops.
     *
     * For the credentials consult <a href="https://mc.commercetools.com">the Merchant Center</a>.
     * @param projectKey the project identifier
     * @param clientId username
     * @param clientSecret password
     * @param authUrl authentication url for the Api
     * @param apiUrl api url for the Api
     * @return sphere client
     */
    default SphereClient createClient(String projectKey, String clientId, String clientSecret, String authUrl, String apiUrl) {
        return createClient(SphereClientConfig.of(projectKey, clientId, clientSecret,authUrl,apiUrl));
    }

    /**
     * Creates a client which relies on an access token and does not refresh it, it reuses an existing http client.
     *
     * A possible use case is keeping the clientSecret secret and/or instantiate a client which is used for few requests for a limited time.
     * The underlying http client does not get closed by closing the client
     *
     * @param config the configuration to use the API
     * @param accessToken the token belonging to the project in {@code config}
     * @param httpClient the http client used for performing requests
     * @return client
     */
    default SphereClient createClientOfApiConfigAndAccessToken(SphereApiConfig config, String accessToken, HttpClient httpClient) {
        final HttpClient uncloseableHttpClient = new HttpClient() {//httpClient should be closed from outside
            @Override
            public CompletionStage<HttpResponse> execute(final HttpRequest httpRequest) {
                return httpClient.execute(httpRequest);
            }

            @Override
            public void close() {
            }
        };
        return SphereClient.of(config, uncloseableHttpClient, SphereAccessTokenSupplier.ofConstantToken(accessToken));
    }

    /**
     * Creates a client which relies on an access token and does not refresh it.
     *
     * A possible use case is keeping the clientSecret secret and/or instantiate a client which is used for few requests for a limited time.
     *
     * @param config the configuration to use the API
     * @param accessToken the token belonging to the project in {@code config}
     * @return client
     */
    default SphereClient createClientOfApiConfigAndAccessToken(SphereApiConfig config, String accessToken) {
        return SphereClient.of(config, createHttpClient(), SphereAccessTokenSupplier.ofConstantToken(accessToken));
    }

    static SphereClientFactory of(final Supplier<HttpClient> httpClientSupplier) {
        return new SphereClientFactoryImpl(httpClientSupplier);
    }

    static SphereClientFactory of() {

        ServiceLoader<SphereHttpClientFactory> loader = ServiceLoader.load(SphereHttpClientFactory.class,SphereClientFactory.class.getClassLoader());
        SphereHttpClientFactory httpClientFactory = loader.iterator().next();
        if(httpClientFactory == null){
            throw new SphereException(new NoClassDefFoundError(SphereHttpClientFactory.class.getCanonicalName()));
        }
        return of(httpClientFactory::getClient);
    }
}
