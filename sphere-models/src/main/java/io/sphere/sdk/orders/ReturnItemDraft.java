package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.Nullable;

@JsonDeserialize(as = ReturnItemDraftImpl.class)
public interface ReturnItemDraft {
    Long getQuantity();

    String getLineItemId();

    ReturnShipmentState getShipmentState();

    @Nullable
    String getComment();

    static ReturnItemDraft of(final long quantity, final String lineItemId, final ReturnShipmentState shipmentState, @Nullable final String comment) {
        return new ReturnItemDraftImpl(quantity, lineItemId, shipmentState, comment);
    }

    static ReturnItemDraft of(final long quantity, final String lineItemId, final ReturnShipmentState shipmentState) {
        return of(quantity, lineItemId, shipmentState, null);
    }
}
