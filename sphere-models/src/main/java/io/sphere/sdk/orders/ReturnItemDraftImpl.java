package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

final class ReturnItemDraftImpl extends Base implements ReturnItemDraft {
    private final Long quantity;
    private final String lineItemId;
    private final ReturnShipmentState shipmentState;
    @Nullable
    private final String comment;

    @JsonCreator
    ReturnItemDraftImpl(final Long quantity, final String lineItemId, final ReturnShipmentState shipmentState, @Nullable final String comment) {
        this.quantity = quantity;
        this.lineItemId = lineItemId;
        this.shipmentState = shipmentState;
        this.comment = comment;
    }

    @Override
    public Long getQuantity() {
        return quantity;
    }

    @Override
    public String getLineItemId() {
        return lineItemId;
    }

    @Override
    public ReturnShipmentState getShipmentState() {
        return shipmentState;
    }

    @Override
    @Nullable
    public String getComment() {
        return comment;
    }
}
