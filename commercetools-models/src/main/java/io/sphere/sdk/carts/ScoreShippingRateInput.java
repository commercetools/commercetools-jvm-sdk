package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

@ResourceValue
@JsonDeserialize(as = ScoreShippingRateInputImpl.class)
public interface ScoreShippingRateInput extends ShippingRateInput{

    @Override
    @JsonIgnore
    default String getType() {
        return "Score";
    }

    Long getScore();

    static ScoreShippingRateInput of(final Long score){
        return new ScoreShippingRateInputImpl(score);
    }


}
