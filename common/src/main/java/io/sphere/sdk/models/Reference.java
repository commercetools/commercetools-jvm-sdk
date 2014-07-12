package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Optional;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class Reference<T> {
    private final String typeId;
    private final String id;
    private final Optional<T> obj;

    @JsonCreator
    public Reference(final String typeId, final String id, final Optional<T> obj) {
        this.id = id;
        this.typeId = typeId;
        this.obj = obj;
    }

    /**
     * Id of the object this reference represents.
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Type id of the object this reference represents, e.g. "customer".
     * @return the type id
     */
    public String getTypeId() {
        return typeId;
    }

    @JsonIgnore
    public Optional<T> getObj() {
        return obj;
    }

    public Reference<T> filled(final T obj) {
        return filled(Optional.fromNullable(obj));
    }

    public Reference<T> filled(final Optional<T> obj) {
        return new Reference<>(getTypeId(), getId(), obj);
    }


    public static <T> Reference<T> of(final String typeId, final String id) {
        return new Reference<>(typeId, id, Optional.absent());
    }

    public static <T> Reference<T> of(final String typeId, final String id, T obj) {
        return Reference.<T>of(typeId, id).filled(obj);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reference reference = (Reference) o;

        if (!id.equals(reference.id)) return false;
        if (!obj.equals(reference.obj)) return false;
        if (!typeId.equals(reference.typeId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + typeId.hashCode();
        result = 31 * result + obj.hashCode();
        return result;
    }
}
