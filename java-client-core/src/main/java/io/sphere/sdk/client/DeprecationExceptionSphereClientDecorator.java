package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.SphereException;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * If SPHERE.IO returns a {@value io.sphere.sdk.client.SphereHttpHeaders#X_DEPRECATION_NOTICE} header field,
 * it does not deserialize the response object in {@link SphereRequest#resultMapper()} but throws a {@link SphereDeprecationException}.
 */
public final class DeprecationExceptionSphereClientDecorator extends SphereClientDecorator implements SphereClient {

    @Override
    public <T> CompletableFuture<T> execute(final SphereRequest<T> sphereRequest) {
        return super.execute(DeprecationHeaderSphereRequest.of(sphereRequest));
    }

    private DeprecationExceptionSphereClientDecorator(final SphereClient delegate) {
        super(delegate);
    }

    public static SphereClient of(final SphereClient delegate) {
        return new DeprecationExceptionSphereClientDecorator(delegate);
    }


    private static final class DeprecationHeaderSphereRequest<T> extends Base implements SphereRequest<T> {
        private final SphereRequest<T> sphereRequest;

        private DeprecationHeaderSphereRequest(final SphereRequest<T> sphereRequest) {
            this.sphereRequest = sphereRequest;
        }

        @Override
        public boolean canDeserialize(final HttpResponse response) {
            return !getDeprecationNoticeHeaderValues(response).isEmpty() || sphereRequest.canDeserialize(response);
        }

        private List<String> getDeprecationNoticeHeaderValues(final HttpResponse response) {
            return response.getHeaders().getHeader(SphereHttpHeaders.X_DEPRECATION_NOTICE);
        }

        @Override
        public HttpRequestIntent httpRequestIntent() {
            return sphereRequest.httpRequestIntent();
        }

        @Override
        public Function<HttpResponse, T> resultMapper() {
            return response -> {
                final List<String> deprecationNoticeHeaderValues = getDeprecationNoticeHeaderValues(response);
                if (deprecationNoticeHeaderValues.isEmpty()) {
                    return sphereRequest.resultMapper().apply(response);
                } else {
                    final SphereException sphereException = new SphereDeprecationException(deprecationNoticeHeaderValues);
                    sphereException.setSphereRequest(sphereRequest);
                    sphereException.setUnderlyingHttpResponse(response);
                    throw sphereException;
                }
            };
        }

        public static <T> SphereRequest<T> of(final SphereRequest<T> sphereRequest) {
            return new DeprecationHeaderSphereRequest<>(sphereRequest);
        }
    }
}
