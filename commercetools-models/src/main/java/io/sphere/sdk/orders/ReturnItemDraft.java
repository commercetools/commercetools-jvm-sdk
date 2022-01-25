package io.sphere.sdk.orders;

import io.sphere.sdk.types.CustomFieldsDraft;
import javax.annotation.Nullable;

public interface ReturnItemDraft {

    Long getQuantity();

    ReturnShipmentState getShipmentState();

    @Nullable
    String getComment();

    @Nullable
    CustomFieldsDraft getCustom();
}
