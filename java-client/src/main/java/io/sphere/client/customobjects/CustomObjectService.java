package io.sphere.client.customobjects;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.customobjects.model.CustomObject;

/** Sphere HTTP API for working with custom objects. */
public interface CustomObjectService {
    /**
     * Finds a custom object by container and key.
     */
    FetchRequest<CustomObject> get(String container, String key);
    <T> CommandRequest<CustomObject> set(String container, String key, T value);
}
