package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Timestamped;

import java.time.Instant;
import java.util.Optional;

public class ReturnItem extends Base implements Timestamped {
    private final String id;
    private final long quantity;
    private final String lineItemId;
    private final Optional<String> comment;
    private final ReturnShipmentState shipmentState;
    private final ReturnPaymentState paymentState;
    private final Instant createdAt;
    private final Instant lastModifiedAt;

    @JsonCreator
    private ReturnItem(final String id, final long quantity, final String lineItemId, final Optional<String> comment, final ReturnShipmentState shipmentState, final ReturnPaymentState paymentState, final Instant createdAt, final Instant lastModifiedAt) {
        this.id = id;
        this.quantity = quantity;
        this.lineItemId = lineItemId;
        this.comment = comment;
        this.shipmentState = shipmentState;
        this.paymentState = paymentState;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    public static ReturnItem of(final String id, final long quantity, final String lineItemId, final Optional<String> comment, final ReturnShipmentState shipmentState, final ReturnPaymentState paymentState, final Instant createdAt, final Instant lastModifiedAt) {
        return new ReturnItem(id, quantity, lineItemId, comment, shipmentState, paymentState, createdAt, lastModifiedAt);

    }

    public String getId() {
        return id;
    }

    public long getQuantity() {
        return quantity;
    }

    public String getLineItemId() {
        return lineItemId;
    }

    public Optional<String> getComment() {
        return comment;
    }

    public ReturnShipmentState getShipmentState() {
        return shipmentState;
    }

    public ReturnPaymentState getPaymentState() {
        return paymentState;
    }

    @Override
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public Instant getLastModifiedAt() {
        return lastModifiedAt;
    }
}
