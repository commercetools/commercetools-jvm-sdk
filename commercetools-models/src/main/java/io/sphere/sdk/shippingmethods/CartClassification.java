package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.products.Price;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

@ResourceValue
@JsonDeserialize(as = CartClassificationImpl.class)
public interface CartClassification extends ShippingRatePriceTier {

    @Override
    default String getType(){
        return "CartClassification";
    }

    String getValue();

    static CartClassification of(final MonetaryAmount price, final String value){
        return new CartClassificationImpl(null,price,value);
    }

}
