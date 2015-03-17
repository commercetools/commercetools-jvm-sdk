package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.ProductType;

public class NestedType extends AttributeTypeBase {
    private final Reference<ProductType> typeReference;

    private NestedType(final Reference<ProductType> typeReference) {
        this.typeReference = typeReference;
    }

    public Reference<ProductType> getTypeReference() {
        return typeReference;
    }

    @JsonIgnore
    public static NestedType of(final Referenceable<ProductType> typeReference) {
        return new NestedType(typeReference.toReference());
    }
}
