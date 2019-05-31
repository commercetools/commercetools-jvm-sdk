package io.sphere.sdk.orders.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface OrderInStoreUpdateByIdCommand extends UpdateCommandDsl<Order, OrderInStoreUpdateByIdCommand>, MetaModelReferenceExpansionDsl<Order, OrderInStoreUpdateByIdCommand, OrderExpansionModel<Order>> {

    static OrderInStoreUpdateByOrderNumberCommand of(final String storeKey, final Versioned<Order> versioned, final List<? extends UpdateAction<Order>> updateActions) {
        return new OrderInStoreUpdateByOrderNumberCommandImpl(storeKey, versioned, updateActions);
    }

    @SafeVarargs
    static OrderInStoreUpdateByOrderNumberCommand of(final String storeKey, final Versioned<Order> versioned, final UpdateAction<Order> updateAction, final UpdateAction<Order>... updateActions) {
        List<UpdateAction<Order>> actions = new ArrayList<>();
        actions.addAll(Arrays.asList(updateActions));
        actions.add(updateAction);
        return new OrderInStoreUpdateByOrderNumberCommandImpl(storeKey, versioned, actions);
    }
}