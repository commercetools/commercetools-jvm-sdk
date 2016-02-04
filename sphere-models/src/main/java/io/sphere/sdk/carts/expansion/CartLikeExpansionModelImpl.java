package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.expansion.ExpansionModel;

/**
 * Internal base class
 * @param <T> context type like {@link io.sphere.sdk.orders.Order} or {@link io.sphere.sdk.carts.Cart}
 */
public abstract class CartLikeExpansionModelImpl<T> extends ExpansionModel<T> implements CartLikeExpansionModel<T> {
    protected CartLikeExpansionModelImpl(final String parentPath, final String path) {
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
        return new LineItemExpansionModel<>(pathExpression(), "lineItems[*]");
    }

    @Override
    public LineItemExpansionModel<T> lineItems(final int index) {
        return new LineItemExpansionModel<>(pathExpression(), "lineItems[" + index + "]");
    }

    @Override
    public PaymentInfoExpansionModel<T> paymentInfo() {
        return new PaymentInfoExpansionModel<>(buildPathExpression(), "paymentInfo");
    }

    private DiscountCodeInfoExpansionModel<T> discountCodes(final String s) {
        return new DiscountCodeInfoExpansionModel<>(pathExpression(), "discountCodes[" + s + "]");
    }

    @Override
    public ShippingInfoExpansionModel<T> shippingInfo() {
        return new ShippingInfoExpansionModel<>(pathExpression(), "shippingInfo");
    }
}
