package io.sphere.sdk.carts;

public interface ScoreShippingRateInput extends ShippingRateInput{

    @Override
    default String getType() {
        return "Score";
    }

    Long getScore();

}
