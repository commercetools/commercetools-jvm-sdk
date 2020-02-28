package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.annotation.Nullable;

final class ReferenceImpl<T> extends Base implements Reference<T> {
    private final String typeId;
    private final String id;
    @Nullable
    private final T obj;

    @JsonCreator
    ReferenceImpl(final String typeId, final String id, @Nullable final T obj) {
        this.id = id;
        this.typeId = typeId;
        this.obj = obj;
    }

    public String getId() {
        return id;
    }

    public String getTypeId() {
        return typeId;
    }

    @Nullable
    public T getObj() {
        return obj;
    }

    public boolean referencesSameResource(final Referenceable<T> counterpart) {
        final Reference<T> reference = counterpart.toReference();
        return reference.getId().equals(getId()) && reference.getTypeId().equals(getTypeId());
    }

    @Override
    public String toString() {
        return "Reference{" +
                "typeId='" + typeId + '\'' +
                ", id='" + id + '\'' +
                ", obj=" + obj +
                '}';
    }

    @SuppressWarnings("rawtypes")//at runtime generic type is not determinable
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Reference)) return false;

        Reference reference = (Reference) o;

        if (!getId().equals(reference.getId())) return false;
        if (!getTypeId().equals(reference.getTypeId())) return false;

        return true;
    }

    @Override
    public final int hashCode() {
        //important, ignore obj hash code to match with equals
        return new HashCodeBuilder(17, 37).
                append(id).
                append(typeId).
                toHashCode();
    }
}
