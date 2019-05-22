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

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

public interface OrderInStoreUpdateByOrderNumberCommand extends UpdateCommandDsl<Order, OrderInStoreUpdateByOrderNumberCommand>, MetaModelReferenceExpansionDsl<Order, OrderInStoreUpdateByOrderNumberCommand, OrderExpansionModel<Order>> {

    static OrderInStoreUpdateByOrderNumberCommand of(final String storeKey, final String orderNumber, final Long version, final List<UpdateAction<Order>> updateActions) {
        final Versioned<Order> versioned = Versioned.of("order-number=" + urlEncode(orderNumber), version);//hack for simple reuse
        return new OrderInStoreUpdateByOrderNumberCommandImpl(storeKey, versioned, updateActions);
    }

    @SafeVarargs
    static OrderInStoreUpdateByOrderNumberCommand of(final String storeKey, final String orderNumber, final Long version, final UpdateAction<Order> updateAction, final UpdateAction<Order>... updateActions) {
        final Versioned<Order> versioned = Versioned.of("order-number=" + urlEncode(orderNumber), version);//hack for simple reuse
        List<UpdateAction<Order>> actions = new ArrayList<>();
        actions.addAll(Arrays.asList(updateActions));
        actions.add(updateAction);
        return new OrderInStoreUpdateByOrderNumberCommandImpl(storeKey, versioned, actions);
    }
}