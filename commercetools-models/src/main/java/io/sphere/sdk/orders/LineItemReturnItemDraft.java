package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;

import javax.annotation.Nullable;

@JsonDeserialize(as = LineItemReturnItemDraftDsl.class)
@ResourceDraftValue(factoryMethods = {
        @FactoryMethod(parameterNames = {"quantity","lineItemId","shipmentState","comment"})
})
public interface LineItemReturnItemDraft extends ReturnItemDraft{

    @Override
    Long getQuantity();

    String getLineItemId();
    @Override
    ReturnShipmentState getShipmentState();

    @Override
    @Nullable
    String getComment();

    static LineItemReturnItemDraft of(final long quantity, final String lineItemId, final ReturnShipmentState shipmentState, @Nullable final String comment) {
        return LineItemReturnItemDraftDsl.of(quantity, lineItemId, shipmentState, comment);
    }

    static LineItemReturnItemDraft of(final long quantity, final String lineItemId, final ReturnShipmentState shipmentState) {
        return of(quantity, lineItemId, shipmentState, null);
    }
}
