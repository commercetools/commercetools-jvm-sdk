package io.sphere.sdk.orders.expansion;

import io.sphere.sdk.carts.expansion.*;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.orders.Order;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public final class OrderExpansionModel<T> extends CartLikeExpansionModel<T> {
    private OrderExpansionModel(final String parentPath, final String path) {
        super(parentPath, path);
    }

    OrderExpansionModel() {
        super();
    }

    public SyncInfoExpansionModel<T> syncInfo() {
        //since it is a set, there is no method with index access
        return new SyncInfoExpansionModel<>(pathExpression(), "syncInfo[*]");
    }

    public ExpansionPathContainer<T> cart() {
        return expansionPath("cart");
    }

    public static OrderExpansionModel<Order> of() {
        return new OrderExpansionModel<>();
    }

    @Override
    public CustomerGroupExpansionModel<T> customerGroup() {
        return super.customerGroup();
    }

    @Override
    public DiscountCodeInfoExpansionModel<T> discountCodes() {
        return super.discountCodes();
    }

    @Override
    public DiscountCodeInfoExpansionModel<T> discountCodes(final int index) {
        return super.discountCodes(index);
    }

    @Override
    public LineItemExpansionModel<T> lineItems() {
        return super.lineItems();
    }

    @Override
    public LineItemExpansionModel<T> lineItems(final int index) {
        return super.lineItems(index);
    }

    @Override
    public PaymentInfoExpansionModel<T> paymentInfo() {
        return super.paymentInfo();
    }

    @Override
    public ShippingInfoExpansionModel<T> shippingInfo() {
        return super.shippingInfo();
    }
}
