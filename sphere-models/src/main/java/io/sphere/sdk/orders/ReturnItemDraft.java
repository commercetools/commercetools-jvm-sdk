package io.sphere.sdk.orders;

import io.sphere.sdk.models.Base;

import java.util.Optional;

public class ReturnItemDraft extends Base {
    private final long quantity;
    private final String lineItemId;
    private final ReturnShipmentState shipmentState;
    private final Optional<String> comment;

    private ReturnItemDraft(final long quantity, final String lineItemId, final ReturnShipmentState shipmentState, final Optional<String> comment) {
        this.quantity = quantity;
        this.lineItemId = lineItemId;
        this.shipmentState = shipmentState;
        this.comment = comment;
    }

    public static ReturnItemDraft of(final long quantity, final String lineItemId, final ReturnShipmentState shipmentState, final Optional<String> comment) {
        return new ReturnItemDraft(quantity, lineItemId, shipmentState, comment);
    }

    public static ReturnItemDraft of(final long quantity, final String lineItemId, final ReturnShipmentState shipmentState, final String comment) {
        return of(quantity, lineItemId, shipmentState, Optional.of(comment));
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

    public Optional<String> getComment() {
        return comment;
    }
}
