package io.sphere.sdk.customobjects.commands;

import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.client.ClientRequestBase;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;

import java.util.function.Function;

/**
 Command for creating or updating a custom object using a custom JSON mapper.
 */
public abstract class CustomObjectCustomJsonMappingUpsertCommand<T> extends ClientRequestBase implements CreateCommand<CustomObject<T>> {

    @Override
    public abstract Function<HttpResponse, CustomObject<T>> resultMapper();

    @Override
    public final HttpRequest httpRequest() {
        return HttpRequest.of(HttpMethod.POST, "/custom-objects", bodyAsJsonString());
    }

    /**
     * Produces JSON to create or update a custom object.
     * It must have the fields of a {@link io.sphere.sdk.customobjects.CustomObject}:
     *
     * Example:
     *
     * <pre>{@code
       {
            "container": "myNamespace",
            "key": "myKey",
            "value": {
                "baz": 3,
                "bar": "a String"
            }
        }
     * }</pre>
     *
     * @return JSON as String
     */
    protected abstract String bodyAsJsonString();
}
