package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

@ResourceValue
@JsonDeserialize(as = ScoreShippingRateInputDraftImpl.class)
public interface ScoreShippingRateInputDraft extends ShippingRateInputDraft{

    @Override
    default String getType() {
        return "Score";
    }

    Long getScore();

    static ScoreShippingRateInputDraft of(final Long score){
        return new ScoreShippingRateInputDraftImpl(score);
    }


}
