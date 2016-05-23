package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.SphereException;

import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * Decorator for {@link SphereClient}s to throw exceptions on deprecated http calls.
 *
 * <p>If the platform returns a {@value io.sphere.sdk.client.SphereHttpHeaders#X_DEPRECATION_NOTICE} header field,
 * it does not deserialize the response object in {@link SphereRequest#deserialize(HttpResponse)} but throws a {@link SphereDeprecationException}.</p>
 */
public final class DeprecationExceptionSphereClientDecorator extends SphereClientDecorator implements SphereClient {

    @Override
    public <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest) {
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
        public boolean canDeserialize(final HttpResponse httpResponse) {
            return !getDeprecationNoticeHeaderValues(httpResponse).isEmpty() || sphereRequest.canDeserialize(httpResponse);
        }

        private List<String> getDeprecationNoticeHeaderValues(final HttpResponse response) {
            return response.getHeaders().getHeader(SphereHttpHeaders.X_DEPRECATION_NOTICE);
        }

        @Override
        public HttpRequestIntent httpRequestIntent() {
            return sphereRequest.httpRequestIntent();
        }

        @Override
        public T deserialize(final HttpResponse httpResponse) {
            final List<String> deprecationNoticeHeaderValues = getDeprecationNoticeHeaderValues(httpResponse);
            if (deprecationNoticeHeaderValues.isEmpty()) {
                return sphereRequest.deserialize(httpResponse);
            } else {
                final SphereException sphereException = new SphereDeprecationException(deprecationNoticeHeaderValues);
                sphereException.setSphereRequest(sphereRequest);
                sphereException.setUnderlyingHttpResponse(httpResponse);
                throw sphereException;
            }
        }

        public static <T> SphereRequest<T> of(final SphereRequest<T> sphereRequest) {
            return new DeprecationHeaderSphereRequest<>(sphereRequest);
        }
    }
}
