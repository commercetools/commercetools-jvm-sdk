package io.sphere.sdk.customobjects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.DefaultModelView;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import java.util.Optional;

@JsonDeserialize(as = CustomObjectImpl.class)
public interface CustomObject<T> extends DefaultModelView<CustomObject<T>>, Referenceable<CustomObject<Void>> {
    String getContainer();

    String getKey();

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
