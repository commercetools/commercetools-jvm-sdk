package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

final class ReturnItemImpl extends Base implements ReturnItem {
    private final String id;
    private final Long quantity;
    private final String lineItemId;
    @Nullable
    private final String comment;
    private final ReturnShipmentState shipmentState;
    private final ReturnPaymentState paymentState;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime lastModifiedAt;

    @JsonCreator
    ReturnItemImpl(final String id, final Long quantity, final String lineItemId, @Nullable final String comment, final ReturnShipmentState shipmentState, final ReturnPaymentState paymentState, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt) {
        this.id = id;
        this.quantity = quantity;
        this.lineItemId = lineItemId;
        this.comment = comment;
        this.shipmentState = shipmentState;
        this.paymentState = paymentState;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    public String getId() {
        return id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public String getLineItemId() {
        return lineItemId;
    }

    @Nullable
    public String getComment() {
        return comment;
    }

    public ReturnShipmentState getShipmentState() {
        return shipmentState;
    }

    public ReturnPaymentState getPaymentState() {
        return paymentState;
    }

    @Override
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public ZonedDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }
}
