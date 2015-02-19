package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpResponse;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * A factory to instantiate SPHERE.IO Java clients which use {@link java.util.concurrent.CompletableFuture} as future implementation.
 *
 * {@include.example example.JavaClientInstantiationExample}
 */
public class SphereClientFactory implements ClientFactory<SphereClient> {
    private SphereClientFactory() {
    }

    @Override
    public SphereClient createClient(final SphereClientConfig config) {
        return SphereClientImpl.of(config);
    }

    public static SphereClientFactory of() {
        return new SphereClientFactory();
    }

    @Override
    public SphereClient createClient(final SphereApiConfig config, final SphereAccessTokenSupplier tokenSupplier) {
        return SphereClientImpl.of(config, tokenSupplier);
    }

    @Override
    public SphereClient createClient(final String projectKey, final String clientId, final String clientSecret) {
        return createClient(SphereClientConfig.of(projectKey, clientId, clientSecret));
    }

    @Override
    public SphereClient createHttpTestDouble(final Function<HttpRequestIntent, HttpResponse> function) {
        return new SphereClient() {
            @Override
            public <T> CompletableFuture<T> execute(final SphereRequest<T> sphereRequest) {
                final HttpRequestIntent httpRequest = sphereRequest.httpRequestIntent();
                final HttpResponse httpResponse = function.apply(httpRequest);
                if (sphereRequest.canHandleResponse(httpResponse)) {
                    final T resultObject = sphereRequest.resultMapper().apply(httpResponse);
                    return CompletableFutureUtils.successful(resultObject);
                } else {
                    throw new UnsupportedOperationException("TODO error case handling");
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

    @SuppressWarnings("unchecked")
    @Override
    public SphereClient createObjectTestDouble(final Function<HttpRequestIntent, Object> function) {
        return new SphereClient() {
            @Override
            public <T> CompletableFuture<T> execute(final SphereRequest<T> sphereRequest) {
                final T result = (T) function.apply(sphereRequest.httpRequestIntent());
                return CompletableFutureUtils.successful(result);
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
