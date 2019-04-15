package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nullable;

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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Nullable
    T getObj();
    
    public static <T> KeyReference<T> of(final String key, final String typeId) {
        return new KeyReferenceImpl<>(key, typeId, null);
    }
    
    public static <T> KeyReference<T> of(final String key, final String typeId, @Nullable final T obj) {
        return new KeyReferenceImpl<>(key, typeId, obj);
    }
    
}
