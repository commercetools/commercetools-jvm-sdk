package io.sphere.sdk.productselections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.HasQueryModel;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Product;


@ResourceValue
@JsonDeserialize(as = ProductSelectionAssignmentImpl.class)
@HasQueryModel
public interface ProductSelectionAssignment {

    Reference<Product> getProduct();

    Reference<ProductSelection> getProductSelection();

    @JsonIgnore
    static ProductSelectionAssignment of(final Reference<Product> productReference, Reference<ProductSelection> productSelectionReference) {
        return new ProductSelectionAssignmentImpl(productReference, productSelectionReference);
    }
}
