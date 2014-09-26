package io.sphere.sdk.attributes;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Reference;

public class RichReferenceType<T> extends ReferenceType {
    private final TypeReference<Reference<T>> typeReference;

    private RichReferenceType(final String referenceTypeId, final TypeReference<Reference<T>> typeReference) {
        super(referenceTypeId);
        this.typeReference = typeReference;
    }

    public TypeReference<Reference<T>> typeReference() {
        return typeReference;
    }

    static <T> RichReferenceType<T> of(final String referenceTypeId, final TypeReference<Reference<T>> typeReference) {
        return new RichReferenceType<>(referenceTypeId, typeReference);
    }
}
