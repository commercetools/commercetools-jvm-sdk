package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;

import javax.annotation.Nullable;

@JsonDeserialize(as = CustomLineItemReturnItemDraftDsl.class)
@ResourceDraftValue(factoryMethods = {
        @FactoryMethod(parameterNames = {"quantity","customLineItemId","shipmentState","comment"})
})
public interface CustomLineItemReturnItemDraft extends ReturnItemDraft{

    @Override
    Long getQuantity();


    String getCustomLineItemId();

    @Override
    ReturnShipmentState getShipmentState();

    @Override
    @Nullable
    String getComment();

    static CustomLineItemReturnItemDraft of(final long quantity, final String lineItemId, final ReturnShipmentState shipmentState, @Nullable final String comment) {
        return CustomLineItemReturnItemDraftDsl.of(quantity, lineItemId, shipmentState, comment);
    }

    static CustomLineItemReturnItemDraft of(final long quantity, final String lineItemId, final ReturnShipmentState shipmentState) {
        return of(quantity, lineItemId, shipmentState, null);
    }
}
