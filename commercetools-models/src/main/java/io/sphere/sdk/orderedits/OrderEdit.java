package io.sphere.sdk.orderedits;

import io.sphere.sdk.annotations.HasUpdateAction;
import io.sphere.sdk.commands.StagedUpdateAction;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

public interface OrderEdit {

    String getId();

    Long getVersion();

    ZonedDateTime getCreatedAt();

    ZonedDateTime getLastModifiedAt();

    @Nullable
    String getKey();

    Reference<Order> getResource();

    List<StagedUpdateAction<OrderEdit>> getStagedActions();

    @Nullable
    CustomFields getCustom();

    OrderEditResult getResult();

    @Nullable
    @HasUpdateAction
    String getComment();
}
