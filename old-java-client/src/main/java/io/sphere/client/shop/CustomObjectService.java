package io.sphere.client.shop;

import io.sphere.client.CommandRequest;
import io.sphere.client.DeleteRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.model.CustomObject;

/** Sphere HTTP API for working with custom objects. */
public interface CustomObjectService {
    /**
     * Finds a custom object by container and key.
     */
    FetchRequest<CustomObject> get(String container, String key);

    /**
     * Sets the custom object identified by container and key
     *
     * Will overwrite all data that already exists under that key.
     */
    <T> CommandRequest<CustomObject> set(String container, String key, T value);

    /**
     * Sets the custom object identified by container and key
     *
     * Will overwrite all data that already exists under that key. Fails with ConcurrentModification error if the version
     * of the existing object does not match the given version.
     */
    <T> CommandRequest<CustomObject> set(String container, String key, T value, int version);

    /**
     *
     * Deletes the object identified by container and key.
     */
    DeleteRequest<CustomObject> delete(String container, String key);
}
