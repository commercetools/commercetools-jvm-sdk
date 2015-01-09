package io.sphere.sdk.customobjects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.DefaultModelView;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import java.util.Optional;

/**
 * Custom objects are a way to store arbitrary JSON-formatted data on the SPHERE.IO platform. It allows you to persist data that does not fit our standard data model.
 *
 * The storage can be seen as key value store but with an additional prefix called container to improve querying custom objects and provide an additional namespace.
 *
 * @param <T> The type of the value of this custom object.
 */
@JsonDeserialize(as = CustomObjectImpl.class)
public interface CustomObject<T> extends DefaultModelView<CustomObject<T>>, Referenceable<CustomObject<Void>> {

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
     * @return
     */
    T getValue();

    @SuppressWarnings("unchecked")
    @Override
    default Reference<CustomObject<Void>> toReference() {
        return new Reference<>(typeId(), getId(), Optional.of((CustomObject<Void>)this));
    }

    public static String typeId(){
        return "key-value-document";
    }
}
