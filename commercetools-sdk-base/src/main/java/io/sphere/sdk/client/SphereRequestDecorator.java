package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;

/**
 * Public available base class to decorate the behaviour of {@link SphereRequest}s.
 * @param <T> the type of the result of this request
 */
public abstract class SphereRequestDecorator<T> extends Base implements SphereRequest<T> {
    protected final SphereRequest<T> delegate;

    protected SphereRequestDecorator(final SphereRequest<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean canDeserialize(final HttpResponse httpResponse) {
        return delegate.canDeserialize(httpResponse);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return delegate.httpRequestIntent();
    }

    @Override
    public T deserialize(final HttpResponse httpResponse) {
        return delegate.deserialize(httpResponse);
    }
}
