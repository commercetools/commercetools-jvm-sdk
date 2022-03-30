package io.sphere.sdk.stores;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.Reference;

import io.sphere.sdk.productselections.ProductSelection;



@ResourceValue
@JsonDeserialize(as = ProductSelectionSettingImpl.class)
public interface ProductSelectionSetting {

    Reference<ProductSelection> getProductSelection();

    Boolean getActive();

    @JsonIgnore
    static ProductSelectionSetting of(final Reference<ProductSelection> productSelectionReference, final Boolean active) {
        return new ProductSelectionSettingImpl(active, productSelectionReference);
    }
}
