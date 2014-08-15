package io.sphere.sdk.commands;

import io.sphere.sdk.annotations.Internal;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpRequest;

import static io.sphere.sdk.utils.JsonUtils.toJson;

/**
 * Base class to implement commands which create an entity in SPHERE.IO.
 *
 * @param <T> the type of the result of the command, most likely the updated entity without expanded references
 * @param <C> class which will serialized as JSON command body, most likely a template
 *
 * <p>Example:</p>
 *
 * {@include.example example.CategoryLifecycleExample#createCategory()}
 */
@Internal
public abstract class CreateCommandImpl<T, C> extends CommandImpl<T> implements CreateCommand<T>{

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
