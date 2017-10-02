package io.sphere.sdk.projects;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

@ResourceValue
@JsonDeserialize(as = CartScoreImpl.class)
public interface CartScore extends ShippingRateInputType{

    @Override
    default String getType(){
        return "CartScore";
    }

    static CartScore of(){
        return new CartScoreImpl();
    }

}
