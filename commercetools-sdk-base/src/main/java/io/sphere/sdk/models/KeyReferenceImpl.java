package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.annotation.Nullable;

class KeyReferenceImpl<T> extends Base implements KeyReference<T> {

    private final String key;
    private final String typeId;
    @Nullable
    private final T obj;

    @JsonCreator
    KeyReferenceImpl(final String key, final String typeId, @Nullable final T obj) {
        this.key = key;
        this.typeId = typeId;
        this.obj = obj;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getTypeId() {
        return typeId;
    }
    
    @Nullable
    public T getObj() {
        return obj;
    }

    @Override
    public String toString() {
        return "Reference{" +
                "typeId='" + typeId + '\'' +
                ", key='" + key + '\'' +
                ", obj=" + obj +
                '}';
    }

    @SuppressWarnings("rawtypes")//at runtime generic type is not determinable
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof KeyReference)) return false;

        KeyReference reference = (KeyReference) o;

        if (!getKey().equals(reference.getKey())) return false;
        if (!getTypeId().equals(reference.getTypeId())) return false;

        return true;
    }

    @Override
    public final int hashCode() {
        //important, ignore obj hash code to match with equals
        return new HashCodeBuilder(17, 37).
                append(key).
                append(typeId).
                toHashCode();
    }

}
