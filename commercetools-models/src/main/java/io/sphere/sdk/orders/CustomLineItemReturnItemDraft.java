package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;

@JsonDeserialize(as = CustomLineItemReturnItemDraftDsl.class)
@ResourceDraftValue(factoryMethods = {
        @FactoryMethod(parameterNames = {"quantity","customLineItemId","shipmentState","comment","custom"})
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

    @Override
    @Nullable
    CustomFieldsDraft getCustom();

    static CustomLineItemReturnItemDraft of(final long quantity, final String lineItemId, final ReturnShipmentState shipmentState, @Nullable final String comment) {
        return CustomLineItemReturnItemDraftDsl.of(quantity, lineItemId, shipmentState, comment, null);
    }

    static CustomLineItemReturnItemDraft of(final long quantity, @Nullable final CustomFieldsDraft custom, final String lineItemId, final ReturnShipmentState shipmentState, @Nullable final String comment) {
        return CustomLineItemReturnItemDraftDsl.of(quantity, lineItemId, shipmentState, comment, custom);
    }

    static CustomLineItemReturnItemDraft of(final long quantity, final String lineItemId, final ReturnShipmentState shipmentState) {
        return of(quantity, null, lineItemId, shipmentState, null);
    }
}
