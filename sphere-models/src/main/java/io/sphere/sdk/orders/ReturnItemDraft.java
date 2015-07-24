package io.sphere.sdk.orders;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

public class ReturnItemDraft extends Base {
    private final long quantity;
    private final String lineItemId;
    private final ReturnShipmentState shipmentState;
    @Nullable
    private final String comment;

    private ReturnItemDraft(final long quantity, final String lineItemId, final ReturnShipmentState shipmentState, @Nullable final String comment) {
        this.quantity = quantity;
        this.lineItemId = lineItemId;
        this.shipmentState = shipmentState;
        this.comment = comment;
    }

    public static ReturnItemDraft of(final long quantity, final String lineItemId, final ReturnShipmentState shipmentState, @Nullable final String comment) {
        return new ReturnItemDraft(quantity, lineItemId, shipmentState, comment);
    }

    public static ReturnItemDraft of(final long quantity, final String lineItemId, final ReturnShipmentState shipmentState) {
        return of(quantity, lineItemId, shipmentState, null);
    }

    public long getQuantity() {
        return quantity;
    }

    public String getLineItemId() {
        return lineItemId;
    }

    public ReturnShipmentState getShipmentState() {
        return shipmentState;
    }

    @Nullable
    public String getComment() {
        return comment;
    }
}
