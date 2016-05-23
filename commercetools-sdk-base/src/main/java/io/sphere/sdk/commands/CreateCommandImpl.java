package io.sphere.sdk.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.http.HttpMethod;

import static io.sphere.sdk.json.SphereJsonUtils.toJsonString;
import static java.util.Objects.requireNonNull;

/**
 * Base class to implement commands which create a resource in the platform.
 *
 * @param <T> the type of the result of the command
 * @param <D> class which will serialized as JSON command body, most likely a template
 */
public abstract class CreateCommandImpl<T, D> extends CommandImpl<T> implements DraftBasedCreateCommand<T, D> {

    private final D body;
    private final String path;
    private final JavaType javaType;

    public CreateCommandImpl(final D draft, final String path, final JavaType javaType) {
        this.body = requireNonNull(draft);
        this.path = requireNonNull(path);
        this.javaType = requireNonNull(javaType);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(httpMethod(), path, httpBody());
    }

    protected HttpMethod httpMethod() {
        return HttpMethod.POST;
    }

    protected String httpBody() {
        return toJsonString(body);
    }

    @Override
    protected JavaType jacksonJavaType() {
        return javaType;
    }

    @Override
    public D getDraft() {
        return body;
    }
}
