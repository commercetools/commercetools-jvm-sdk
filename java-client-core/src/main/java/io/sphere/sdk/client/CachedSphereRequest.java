package io.sphere.sdk.client;

final class CachedSphereRequest<T> extends SphereRequestDecorator<T> {
    private final HttpRequestIntent httpRequest;

    private CachedSphereRequest(final SphereRequest<T> delegate) {
        super(delegate);
        this.httpRequest = delegate.httpRequestIntent();
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return httpRequest;
    }

    static <T> SphereRequest<T> of(final SphereRequest<T> delegate) {
        return new CachedSphereRequest<>(delegate);
    }
}
