package io.sphere.sdk.client;

public abstract class BaseQuery<I, R> implements Query<I, R>, Requestable {
    protected abstract String endPoint();

    @Override
    public HttpRequest httpRequest() {
        return HttpRequest.of(HttpMethod.GET, endPoint());
    }
}
