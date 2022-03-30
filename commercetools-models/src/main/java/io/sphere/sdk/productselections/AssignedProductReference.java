package io.sphere.sdk.productselections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Product;


@ResourceValue
@JsonDeserialize(as = AssignedProductReferenceImpl.class)
public interface AssignedProductReference {

    Reference<Product> getProduct();

    @JsonIgnore
    static AssignedProductReference of(final Reference<Product> productReference) {
        return new AssignedProductReferenceImpl(productReference);
    }
}
