package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;

/**
 * A wrapper for a {@link SphereRequest} to receive the Java object as well as the {@link HttpResponse} representation.
 *
 * {@include.example io.sphere.sdk.client.JavaAndHttpResponseSphereRequestIntegrationTest#execute()}
 *
 * @param <T> the Java class of the originals {@link SphereRequest}s result.
 */
public final class JavaAndHttpResponseSphereRequest<T> extends Base implements SphereRequest<Pair<T, HttpResponse>> {
    private final SphereRequest<T> delegate;

    private JavaAndHttpResponseSphereRequest(final SphereRequest<T> delegate) {
        this.delegate = delegate;
    }

    public static <T> JavaAndHttpResponseSphereRequest<T> of(final SphereRequest<T> delegate) {
        return new JavaAndHttpResponseSphereRequest<>(delegate);
    }

    @Nullable
    @Override
    public Pair<T, HttpResponse> deserialize(final HttpResponse httpResponse) {
        final T javaObject = delegate.deserialize(httpResponse);
        return ImmutablePair.of(javaObject, httpResponse);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return delegate.httpRequestIntent();
    }

    @Override
    public boolean canDeserialize(final HttpResponse httpResponse) {
        return delegate.canDeserialize(httpResponse);
    }
}
