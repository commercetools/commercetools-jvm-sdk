package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.expansion.ExpansionModel;

/**
 * Internal base class
 * @param <T> context of the reference expansion
 */
public abstract class CartLikeExpansionModel<T> extends ExpansionModel<T> {
    protected CartLikeExpansionModel(final String parentPath, final String path) {
        super(parentPath, path);
    }

    protected CartLikeExpansionModel() {
        super();
    }

    public CustomerGroupExpansionModel<T> customerGroup() {
        return CustomerGroupExpansionModel.of(buildPathExpression(), "customerGroup");
    }

    public DiscountCodeInfoExpansionModel<T> discountCodes() {
        return discountCodes("*");
    }

    public DiscountCodeInfoExpansionModel<T> discountCodes(final int index) {
        return discountCodes("" + index);
    }

    public LineItemExpansionModel<T> lineItems() {
        return new LineItemExpansionModel<>(pathExpression(), "lineItems[*]");
    }

    public LineItemExpansionModel<T> lineItems(final int index) {
        return new LineItemExpansionModel<>(pathExpression(), "lineItems[" + index + "]");
    }

    public PaymentInfoExpansionModel<T> paymentInfo() {
        return new PaymentInfoExpansionModel<>(buildPathExpression(), "paymentInfo");
    }

    private DiscountCodeInfoExpansionModel<T> discountCodes(final String s) {
        return new DiscountCodeInfoExpansionModel<>(pathExpression(), "discountCodes[" + s + "]");
    }

    public ShippingInfoExpansionModel<T> shippingInfo() {
        return new ShippingInfoExpansionModel<>(pathExpression(), "shippingInfo");
    }
}
