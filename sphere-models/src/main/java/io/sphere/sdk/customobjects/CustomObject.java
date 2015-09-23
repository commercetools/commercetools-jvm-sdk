package io.sphere.sdk.customobjects;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.ResourceView;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

/**
 * Custom objects are a way to store arbitrary JSON-formatted data on the SPHERE.IO platform. It allows you to persist data that does not fit our standard data model.
 *
 * The storage can be seen as key value store but with an additional prefix called container to improve querying custom objects and provide an additional namespace.
 *
 * @param <T> The type of the value of this custom object.
 */
@JsonDeserialize(as = CustomObjectImpl.class)
public interface CustomObject<T> extends ResourceView<CustomObject<T>, CustomObject<JsonNode>>, Referenceable<CustomObject<JsonNode>> {

    /**
     * The container is part of the custom object namespace to find it
     * @return container
     */
    String getContainer();

    /**
     * The key is part of the custom object namespace to find it
     * @return container
     */
    String getKey();

    /**
     * The value stored in the custom object.
     * @return the value
     */
    T getValue();

    @Override
    default Reference<CustomObject<JsonNode>> toReference() {
        return Reference.of(referenceTypeId(), getId());//not possible to provide filled reference since type can be different
    }

    static String referenceTypeId() {
        return "key-value-document";
    }

    /**
     *
     * @deprecated use {@link #referenceTypeId()} instead
     */
    @Deprecated
    static String typeId() {
        return "key-value-document";
    }

    static String validatedKey(final String key) {
        return CustomObjectImpl.validatedKey("key", key);
    }

    static String validatedContainer(final String container) {
        return CustomObjectImpl.validatedKey("container", container);
    }
}
