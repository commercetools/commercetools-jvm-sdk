package io.sphere.sdk.orders.expansion;

import io.sphere.sdk.carts.expansion.*;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.states.expansion.StateExpansionModel;

import java.util.List;

final class OrderExpansionModelImpl<T> extends CartLikeExpansionModelImpl<T> implements OrderExpansionModel<T> {

    OrderExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    OrderExpansionModelImpl() {
        super();
    }

    @Override
    public SyncInfoExpansionModel<T> syncInfo() {
        //since it is a set, there is no method with index access
        return new SyncInfoExpansionModelImpl<>(pathExpression(), "syncInfo[*]");
    }

    @Override
    public CartExpansionModel<T> cart() {
        return CartExpansionModel.of(pathExpression(), "cart");
    }

    @Override
    public StateExpansionModel<T> state() {
        return StateExpansionModel.of(pathExpression(), "state");
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
