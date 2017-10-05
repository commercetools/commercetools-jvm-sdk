package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;

@ResourceValue
@JsonDeserialize(as = ClassificationShippingRateInputImpl.class)
public interface ClassificationShippingRateInput extends ShippingRateInput{

    @Override
    @JsonIgnore
    default String getType() {
        return "Classification";
    }

    String getKey();

    @Nullable
    LocalizedString getLabel();


    static ClassificationShippingRateInput of(final String key){
        return new ClassificationShippingRateInputImpl(key, null);
    }

}
