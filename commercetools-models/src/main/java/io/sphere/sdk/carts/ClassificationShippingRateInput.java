package io.sphere.sdk.carts;

import io.sphere.sdk.models.LocalizedString;

public interface ClassificationShippingRateInput extends ShippingRateInput{

    @Override
    default String getType() {
        return "Classification";
    }

    String getKey();

    LocalizedString getLabel();

}
