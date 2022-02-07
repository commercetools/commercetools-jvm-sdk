package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.Timestamped;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@ResourceValue
@JsonDeserialize(as = CustomLineItemReturnItemImpl.class)
public interface CustomLineItemReturnItem extends ReturnItem {
    static CustomLineItemReturnItem of(@Nullable final String comment, final ZonedDateTime createdAt,
                                       final String customLineItemId, final String id, final ZonedDateTime lastModifiedAt , final ReturnPaymentState paymentState, final Long quantity,
                                       final ReturnShipmentState shipmentState) {
        return new CustomLineItemReturnItemImpl(comment, createdAt, null, customLineItemId, id, lastModifiedAt, paymentState, quantity, shipmentState);
    }

    static CustomLineItemReturnItem of(@Nullable final String comment, final ZonedDateTime createdAt,
                                       final String customLineItemId, final String id, final ZonedDateTime lastModifiedAt , final ReturnPaymentState paymentState, final Long quantity,
                                       final ReturnShipmentState shipmentState, @Nullable final CustomFields custom) {
        return new CustomLineItemReturnItemImpl(comment, createdAt, custom, customLineItemId, id, lastModifiedAt, paymentState, quantity, shipmentState);
    }

    String getCustomLineItemId();
}
