package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

@JsonDeserialize(as=ProductProjectionImpl.class)
public interface ProductProjection extends ProductLike<ProductProjection>, ProductDataLike, Referenceable<Product> {

    public boolean hasStagedChanges();

    public boolean isPublished();

    @Override
    default Reference<Product> toReference() {
        return Product.reference(getId());
    }

    public static TypeReference<ProductProjection> typeReference(){
        return new TypeReference<ProductProjection>() {
            @Override
            public String toString() {
                return "TypeReference<ProductProjection>";
            }
        };
    }
}
