package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

public final class SetLineItemPrice extends OrderEditStagedUpdateActionBase {

    final private String lineItemId;

    @Nullable
    final private MonetaryAmount externalPrice;

    @JsonCreator
    private SetLineItemPrice(final String lineItemId, @Nullable final MonetaryAmount externalPrice) {
        super("setLineItemPrice");
        this.lineItemId = lineItemId;
        this.externalPrice = externalPrice;
    }

    public static SetLineItemPrice of(final String lineItemId, @Nullable final MonetaryAmount externalPrice) {
        return new SetLineItemPrice(lineItemId, externalPrice);
    }

    public String getLineItemId() {
        return lineItemId;
    }

    @Nullable
    public MonetaryAmount getExternalPrice() {
        return externalPrice;
    }
}