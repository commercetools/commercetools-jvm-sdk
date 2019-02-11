package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.carts.ShippingRateInputDraft;

import javax.annotation.Nullable;

public final class SetShippingRateInput extends OrderEditStagedUpdateActionBase {

    @Nullable
    private final ShippingRateInputDraft shippingRateInput;

    @JsonCreator
    private SetShippingRateInput(@Nullable final ShippingRateInputDraft shippingRateInput) {
        super("setShippingRateInput");
        this.shippingRateInput = shippingRateInput;
    }

    @Nullable
    @JsonProperty("shippingRateInput")
    public ShippingRateInputDraft getShippingRateInput() {
        return shippingRateInput;
    }

    public static SetShippingRateInput of(@Nullable final ShippingRateInputDraft shippingRateInput) {
        return new SetShippingRateInput(shippingRateInput);
    }

    public static SetShippingRateInput ofUnset() {
        return new SetShippingRateInput(null);
    }
}