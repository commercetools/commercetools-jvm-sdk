package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.LocalizedString;

@ResourceValue
@JsonDeserialize(as = ClassificationShippingRateInputImpl.class)
public interface ClassificationShippingRateInput extends ShippingRateInput{

    @Override
    default String getType() {
        return "Classification";
    }

    String getKey();

    LocalizedString getLabel();


    static ClassificationShippingRateInput of(final String key, final LocalizedString label){
        return new ClassificationShippingRateInputImpl(key, label);
    }

}
