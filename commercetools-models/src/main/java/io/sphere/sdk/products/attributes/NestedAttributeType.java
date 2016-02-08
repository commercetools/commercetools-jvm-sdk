package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.producttypes.ProductType;

public final class NestedAttributeType extends AttributeTypeBase {
    private final Reference<ProductType> typeReference;

    @JsonCreator
    private NestedAttributeType(final Reference<ProductType> typeReference) {
        this.typeReference = typeReference;
    }

    public Reference<ProductType> getTypeReference() {
        return typeReference;
    }

    @JsonIgnore
    public static NestedAttributeType of(final Referenceable<ProductType> typeReference) {
        return new NestedAttributeType(typeReference.toReference());
    }
}
