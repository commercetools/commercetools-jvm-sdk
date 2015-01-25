package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * A factory to instantiate SPHERE.IO Java clients which use {@link java.util.concurrent.CompletableFuture} as future implementation.
 */
public class SphereClientFactory implements ClientFactory<SphereClient> {
    private SphereClientFactory() {
    }

    @Override
    public SphereClient createClient(final SphereClientConfig config) {
        return new SphereClientImpl(config);
    }

    public static SphereClientFactory of() {
        return new SphereClientFactory();
    }

    @Override
    public SphereClient createClient(final SphereApiConfig config, final SphereAccessTokenSupplier tokenSupplier) {
        return new SphereClientImpl(config, tokenSupplier);
    }

    @Override
    public SphereClient createHttpTestDouble(final Function<HttpRequest, HttpResponse> function) {
        return new SphereClient() {
            @Override
            public <T> CompletableFuture<T> execute(final SphereRequest<T> sphereRequest) {
                final HttpRequest httpRequest = sphereRequest.httpRequest();
                final HttpResponse httpResponse = function.apply(httpRequest);
                if (sphereRequest.canHandleResponse(httpResponse)) {
                    final T resultObject = sphereRequest.resultMapper().apply(httpResponse);
                    return CompletableFutureUtils.fullFilled(resultObject);
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
    public SphereClient createObjectTestDouble(final Function<HttpRequest, Object> function) {
        return new SphereClient() {
            @Override
            public <T> CompletableFuture<T> execute(final SphereRequest<T> sphereRequest) {
                final T result = (T) function.apply(sphereRequest.httpRequest());
                return CompletableFutureUtils.fullFilled(result);
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
