package io.sphere.sdk.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.annotations.Internal;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.client.JsonEndpoint;

import static io.sphere.sdk.json.JsonUtils.toJson;

/**
 * Base class to implement commands which create an entity in SPHERE.IO.
 *
 * @param <T> the type of the result of the command, most likely the updated entity without expanded references
 * @param <C> class which will serialized as JSON command body, most likely a template
 */
@Internal
public abstract class CreateCommandImpl<T, C> extends CommandImpl<T> implements CreateCommand<T>{

    private final C body;
    private final JsonEndpoint<T> endpoint;

    public CreateCommandImpl(final C body, final JsonEndpoint<T> endpoint) {
        this.body = body;
        this.endpoint = endpoint;
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(httpMethod(), endpoint.endpoint(), httpBody());
    }

    protected HttpMethod httpMethod() {
        return HttpMethod.POST;
    }

    protected String httpBody() {
        return toJson(body);
    }

    @Override
    protected TypeReference<T> typeReference() {
        return endpoint.typeReference();
    }
}
