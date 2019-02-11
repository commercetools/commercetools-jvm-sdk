package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.carts.ItemShippingDetailsDraft;

import javax.annotation.Nullable;

public final class SetLineItemShippingDetails extends OrderEditStagedUpdateActionBase {

    private final String lineItemId;

    @Nullable
    private final ItemShippingDetailsDraft shippingDetails;

    @JsonCreator
    private SetLineItemShippingDetails(final String lineItemId, @Nullable final ItemShippingDetailsDraft shippingDetails) {
        super("setLineItemShippingDetails");
        this.lineItemId = lineItemId;
        this.shippingDetails = shippingDetails;
    }

    public static SetLineItemShippingDetails of(final String lineItemId, @Nullable final ItemShippingDetailsDraft shippingDetails) {
        return new SetLineItemShippingDetails(lineItemId, shippingDetails);
    }

    public static SetLineItemShippingDetails of(final String lineItemId) {
        return new SetLineItemShippingDetails(lineItemId, null);
    }

    public String getLineItemId() {
        return lineItemId;
    }

    @Nullable
    public ItemShippingDetailsDraft getShippingDetails() {
        return shippingDetails;
    }
}