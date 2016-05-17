package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Timestamped;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@JsonDeserialize(as = ReturnItemImpl.class)
public interface ReturnItem extends Timestamped {
    static ReturnItem of(final String id, final Long quantity, final String lineItemId, final String comment, final ReturnShipmentState shipmentState, final ReturnPaymentState paymentState, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt) {
        return new ReturnItemImpl(id, quantity, lineItemId, comment, shipmentState, paymentState, createdAt, lastModifiedAt);

    }

    String getId();

    Long getQuantity();

    String getLineItemId();

    @Nullable
    String getComment();

    ReturnShipmentState getShipmentState();

    ReturnPaymentState getPaymentState();

    @Override
    ZonedDateTime getCreatedAt();

    @Override
    ZonedDateTime getLastModifiedAt();
}
