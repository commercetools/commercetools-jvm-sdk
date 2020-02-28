package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.Nullable;

@JsonDeserialize(as = KeyReferenceImpl.class)
public interface KeyReference<T>  {

    /**
     * Key of the object this reference represents.
     * @return the id
     */
    String getKey();

    /**
     * Type id of the object this reference represents, e.g. "customer".
     * @return the type id
     */
    String getTypeId();

    @Nullable
    T getObj();
    
    public static <T> KeyReference<T> of(final String key, final String typeId) {
        return new KeyReferenceImpl<>(key, typeId, null);
    }
    
    public static <T> KeyReference<T> of(final String key, final String typeId, @Nullable final T obj) {
        return new KeyReferenceImpl<>(key, typeId, obj);
    }
    
}
