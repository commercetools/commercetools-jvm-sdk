package io.sphere.sdk.requests;

import io.sphere.sdk.annotations.Internal;

import static io.sphere.sdk.utils.JsonUtils.toJson;

/**
 *
 * @param <I> interface type that is returned
 * @param <C> class which will serialized as JSON command
 */
@Internal
public abstract class CreateCommandImpl<I, C> extends CommandImpl<I> {

    private final C body;

    protected CreateCommandImpl(final C body) {
        this.body = body;
    }

    @Override
    public HttpRequest httpRequest() {
        return HttpRequest.of(httpMethod(), httpEndpoint(), httpBody());
    }

    protected HttpMethod httpMethod() {
        return HttpMethod.POST;
    }

    protected String httpBody() {
        return toJson(body);
    }

    protected abstract String httpEndpoint();
}
