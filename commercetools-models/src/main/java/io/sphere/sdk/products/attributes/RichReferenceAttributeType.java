package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Reference;

public final class RichReferenceAttributeType<T> extends ReferenceAttributeType {
    private final TypeReference<Reference<T>> typeReference;

    @JsonCreator
    private RichReferenceAttributeType(final String referenceTypeId, final TypeReference<Reference<T>> typeReference) {
        super(referenceTypeId);
        this.typeReference = typeReference;
    }

    public TypeReference<Reference<T>> typeReference() {
        return typeReference;
    }

    static <T> RichReferenceAttributeType<T> of(final String referenceTypeId, final TypeReference<Reference<T>> typeReference) {
        return new RichReferenceAttributeType<>(referenceTypeId, typeReference);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (typeReference != null ? typeReference.hashCode() : 0);
        return result;
    }
}
