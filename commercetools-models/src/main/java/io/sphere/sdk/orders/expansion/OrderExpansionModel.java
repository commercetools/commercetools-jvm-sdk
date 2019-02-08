package io.sphere.sdk.orders.expansion;

import io.sphere.sdk.carts.expansion.*;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.states.expansion.StateExpansionModel;

import java.util.List;

/**
 DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public interface OrderExpansionModel<T> extends ExpansionPathContainer<T>, CartLikeExpansionModel<T> {
    SyncInfoExpansionModel<T> syncInfo();

    CartExpansionModel<T> cart();

    StateExpansionModel<T> state();

    @Override
    CustomerGroupExpansionModel<T> customerGroup();

    @Override
    DiscountCodeInfoExpansionModel<T> discountCodes();

    @Override
    DiscountCodeInfoExpansionModel<T> discountCodes(int index);

    @Override
    LineItemExpansionModel<T> lineItems();

    @Override
    LineItemExpansionModel<T> lineItems(int index);

    @Override
    PaymentInfoExpansionModel<T> paymentInfo();

    @Override
    ShippingInfoExpansionModel<T> shippingInfo();

    static OrderExpansionModel<Order> of() {
        return new OrderExpansionModelImpl<>();
    }

    static <T> OrderExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new OrderExpansionModelImpl<>(parentPath, path);
    }
}
