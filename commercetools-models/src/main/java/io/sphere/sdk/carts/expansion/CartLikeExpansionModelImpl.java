package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.expansion.ExpansionModelImpl;

import java.util.List;

/**
 * Internal base class
 * @param <T> context type like {@link io.sphere.sdk.orders.Order} or {@link io.sphere.sdk.carts.Cart}
 */
public abstract class CartLikeExpansionModelImpl<T> extends ExpansionModelImpl<T> implements CartLikeExpansionModel<T> {
    protected CartLikeExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    protected CartLikeExpansionModelImpl() {
        super();
    }

    @Override
    public CustomerGroupExpansionModel<T> customerGroup() {
        return CustomerGroupExpansionModel.of(buildPathExpression(), "customerGroup");
    }

    @Override
    public DiscountCodeInfoExpansionModel<T> discountCodes() {
        return discountCodes("*");
    }

    @Override
    public DiscountCodeInfoExpansionModel<T> discountCodes(final int index) {
        return discountCodes("" + index);
    }

    @Override
    public LineItemExpansionModel<T> lineItems() {
        return new LineItemExpansionModelImpl<>(pathExpression(), "lineItems[*]");
    }

    @Override
    public LineItemExpansionModel<T> lineItems(final int index) {
        return new LineItemExpansionModelImpl<>(pathExpression(), "lineItems[" + index + "]");
    }

    @Override
    public CustomLineItemExpansionModel<T> customLineItems() {
        return new CustomLineItemExpansionModelImpl<>(pathExpression(), "customLineItems[*]");
    }

    @Override
    public CustomLineItemExpansionModel<T> customLineItems(final int index) {
        return new CustomLineItemExpansionModelImpl<>(pathExpression(), "customLineItems[" + index + "]");
    }

    @Override
    public PaymentInfoExpansionModel<T> paymentInfo() {
        return new PaymentInfoExpansionModelImpl<>(buildPathExpression(), "paymentInfo");
    }

    private DiscountCodeInfoExpansionModel<T> discountCodes(final String s) {
        return new DiscountCodeInfoExpansionModelImpl<>(pathExpression(), "discountCodes[" + s + "]");
    }

    @Override
    public ShippingInfoExpansionModel<T> shippingInfo() {
        return new ShippingInfoExpansionModelImpl<>(pathExpression(), "shippingInfo");
    }

}
