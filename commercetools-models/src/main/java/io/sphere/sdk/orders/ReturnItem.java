package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.sphere.sdk.carts.ClassificationShippingRateInputDraftDsl;
import io.sphere.sdk.carts.ScoreShippingRateInputDraftDsl;
import io.sphere.sdk.models.Timestamped;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CustomLineItemReturnItem.class, name = "CustomLineItemReturnItem"),
        @JsonSubTypes.Type(value = LineItemReturnItem.class, name = "LineItemReturnItem")
})
public interface ReturnItem extends Timestamped {

    String getId();

    Long getQuantity();

    @Nullable
    String getComment();

    ReturnShipmentState getShipmentState();

    ReturnPaymentState getPaymentState();

    @Nullable
    CustomFields getCustom();

    @Override
    ZonedDateTime getCreatedAt();

    @Override
    ZonedDateTime getLastModifiedAt();

    static String referenceTypeId() {
        return "order-return-item";
    }
}
