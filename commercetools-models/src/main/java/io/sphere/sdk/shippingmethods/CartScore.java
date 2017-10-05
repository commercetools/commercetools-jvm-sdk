package io.sphere.sdk.shippingmethods;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.products.Price;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

@ResourceValue
@JsonDeserialize(as = CartScoreImpl.class)
public interface CartScore extends ShippingRatePriceTier {


    @Override
    default String getType(){
        return "CartScore";
    }

    @Nullable
    Long getScore();

    @Nullable
    PriceFunction getPriceFunction();

    static CartScore ofPriceFunction(final MonetaryAmount price, final PriceFunction priceFunction){
        return new CartScoreImpl(null, price, priceFunction, null);
    }

    static CartScore ofScore(final MonetaryAmount price, final Long score){
        return new CartScoreImpl(null, price, null, score);
    }
}
