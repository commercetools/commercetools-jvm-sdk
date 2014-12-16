package io.sphere.sdk.client;

import io.sphere.sdk.http.ClientRequest;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;

import java.util.function.Function;

final class CachedHttpRequestClientRequest<T> extends Base implements ClientRequest<T> {
    private final ClientRequest<T> delegate;
    private final HttpRequest httpRequest;

    CachedHttpRequestClientRequest(final ClientRequest<T> delegate) {
        this.delegate = delegate;
        this.httpRequest = delegate.httpRequest();
    }

    @Override
    public Function<HttpResponse, T> resultMapper() {
        return delegate.resultMapper();
    }

    @Override
    public HttpRequest httpRequest() {
        return httpRequest;
    }

    @Override
    public boolean canHandleResponse(final HttpResponse response) {
        return delegate.canHandleResponse(response);
    }
}
