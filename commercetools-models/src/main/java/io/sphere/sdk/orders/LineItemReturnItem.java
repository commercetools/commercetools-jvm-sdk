package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.Timestamped;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@ResourceValue
@JsonDeserialize(as = LineItemReturnItemImpl.class)
public interface LineItemReturnItem extends ReturnItem {
    static LineItemReturnItem of(final String id, final Long quantity, final String lineItemId, final String comment, final ReturnShipmentState shipmentState, final ReturnPaymentState paymentState, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt) {
        return new LineItemReturnItemImpl(comment, createdAt, null, id, lastModifiedAt, lineItemId, paymentState, quantity, shipmentState);
    }

    static LineItemReturnItem of(final String id, final Long quantity, final String lineItemId, final String comment, final ReturnShipmentState shipmentState, final ReturnPaymentState paymentState, @Nullable final CustomFields custom, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt) {
        return new LineItemReturnItemImpl(comment, createdAt, custom, id, lastModifiedAt, lineItemId, paymentState, quantity, shipmentState);
    }

    String getLineItemId();
}
