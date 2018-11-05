package io.sphere.sdk.orders;

import javax.annotation.Nullable;

public interface ReturnItemDraft {

    Long getQuantity();

    ReturnShipmentState getShipmentState();

    @Nullable
    String getComment();

}
