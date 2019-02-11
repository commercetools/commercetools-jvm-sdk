package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.carts.ExternalLineItemTotalPrice;

import javax.annotation.Nullable;

public final class SetLineItemTotalPrice extends OrderEditStagedUpdateActionBase {

    @Nullable
    private final ExternalLineItemTotalPrice externalTotalPrice;

    private final String lineItemId;

    @JsonCreator
    private SetLineItemTotalPrice(final String lineItemId, @Nullable final ExternalLineItemTotalPrice externalTotalPrice) {
        super("setLineItemTotalPrice");
        this.lineItemId = lineItemId;
        this.externalTotalPrice = externalTotalPrice;
    }

    public static SetLineItemTotalPrice of(final String lineItemId, @Nullable final ExternalLineItemTotalPrice externalTotalPrice) {
        return new SetLineItemTotalPrice(lineItemId, externalTotalPrice);
    }

    @Nullable
    public ExternalLineItemTotalPrice getExternalTotalPrice() {
        return externalTotalPrice;
    }

    public String getLineItemId() {
        return lineItemId;
    }
}