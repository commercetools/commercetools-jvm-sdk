package io.sphere.sdk.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Versioned;

import static java.util.Objects.requireNonNull;

/**
 Internal base class to implement commands which deletes a resource by ID in SPHERE.IO.
 @param <T> the type of the result of the command, most likely the updated resource without expanded references */
public abstract class ByIdDeleteCommandImpl<T> extends CommandImpl<T> implements DeleteCommand<T> {
    private final Versioned<T> versioned;
    private final JavaType javaType;
    private final String path;

    protected ByIdDeleteCommandImpl(final Versioned<T> versioned, final JsonEndpoint<T> endpoint) {
        requireNonNull(endpoint);
        this.versioned = requireNonNull(versioned);
        this.javaType = SphereJsonUtils.convertToJavaType(endpoint.typeReference());
        this.path = endpoint.endpoint();
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        final String baseEndpointWithoutId = path;
        if (!baseEndpointWithoutId.startsWith("/")) {
            throw new RuntimeException("By convention the paths start with a slash, see baseEndpointWithoutId()");
        }
        return HttpRequestIntent.of(HttpMethod.DELETE, baseEndpointWithoutId + "/" + versioned.getId() + "?version=" + versioned.getVersion());
    }

    @Override
    protected JavaType jacksonJavaType() {
        return javaType;
    }
}
