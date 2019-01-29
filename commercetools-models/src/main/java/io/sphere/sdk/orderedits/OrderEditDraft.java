package io.sphere.sdk.orderedits;

import io.sphere.sdk.commands.StagedUpdateAction;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.List;

public interface OrderEditDraft {

    @Nullable
    String getKey();

    Reference<Order> getResource();

    List<StagedUpdateAction<OrderEdit>> getStagedActions();

    @Nullable
    CustomFieldsDraft getCustom();

    @Nullable
    String getComment();

    @Nullable
    Boolean isDryRun();
}