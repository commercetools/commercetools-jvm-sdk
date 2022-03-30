package io.sphere.sdk.productselections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.Reference;


@ResourceValue
@JsonDeserialize(as = AssignedProductSelectionImpl.class)
public interface AssignedProductSelection {

    Reference<ProductSelection> getProductSelection();

    @JsonIgnore
    static AssignedProductSelection of(final Reference<ProductSelection> productSelectionReference) {
        return new AssignedProductSelectionImpl(productSelectionReference);
    }
}
