package io.sphere.sdk.customobjects.queries;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.queries.Get;

/**
 * {@link io.sphere.sdk.client.SphereRequest} to fetch one {@link io.sphere.sdk.customobjects.CustomObject} by container and key but using a custom JSON mapper instead of the SDK default one.
 *
 * <p>Example for an implementation with Google GSON (multiple code snippets):</p>
 *
 * <p>The class of the custom object value:</p>
 * {@include.example io.sphere.sdk.customobjects.demo.GsonFoo}
 *
 * <p>The class including the wrapper custom object:</p>
 * {@include.example io.sphere.sdk.customobjects.demo.GsonFooCustomObject}
 *
 * <p>The implementation of the getter:</p>
 * {@include.example io.sphere.sdk.customobjects.demo.GsonFooCustomObjectByKeyGet}
 *
 * <p>An execution example:</p>
 * {@include.example io.sphere.sdk.customobjects.queries.CustomObjectCustomJsonMappingByKeyGetIntegrationTest#execution()}
 * @param <T> The type of the value of the custom object.
 * @see CustomObject
 */
public abstract class CustomObjectCustomJsonMappingByKeyGet<T> extends CustomObjectCustomJsonMappingByXGet<T> implements Get<CustomObject<T>> {

    public CustomObjectCustomJsonMappingByKeyGet(final String container, final String key) {
        super("/" + container + "/" + key);
    }
}

