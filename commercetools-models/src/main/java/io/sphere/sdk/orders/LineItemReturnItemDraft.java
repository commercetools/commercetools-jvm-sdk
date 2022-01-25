package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;

@JsonDeserialize(as = LineItemReturnItemDraftDsl.class)
@ResourceDraftValue(factoryMethods = {
        @FactoryMethod(parameterNames = {"quantity","lineItemId","shipmentState","comment", "custom"})
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

    @Override
    @Nullable
    CustomFieldsDraft getCustom();

    static LineItemReturnItemDraft of(final long quantity, final String lineItemId, final ReturnShipmentState shipmentState, @Nullable final String comment) {
        return LineItemReturnItemDraftDsl.of(quantity, lineItemId, shipmentState, comment, null);
    }

    static LineItemReturnItemDraft of(final long quantity, final String lineItemId, final ReturnShipmentState shipmentState, @Nullable final String comment, @Nullable final CustomFieldsDraft custom) {
        return LineItemReturnItemDraftDsl.of(quantity, lineItemId, shipmentState, comment, custom);
    }

    static LineItemReturnItemDraft of(final long quantity, final String lineItemId, final ReturnShipmentState shipmentState) {
        return of(quantity, lineItemId, shipmentState, null, null);
    }
}
