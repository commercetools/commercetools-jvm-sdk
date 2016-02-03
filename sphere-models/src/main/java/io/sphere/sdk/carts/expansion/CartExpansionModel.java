package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.expansion.ExpansionPathContainer;

/**
 * Model to explore reference fields in a cart that can be expanded.
 *
 * @param <T> the context type
 */
public final class CartExpansionModel<T> extends CartLikeExpansionModel<T> {
    private CartExpansionModel() {
        super();
    }

    public static CartExpansionModel<Cart> of() {
        return new CartExpansionModel<>();
    }

    protected CartExpansionModel(final String parentPath, final String path) {
        super(parentPath, path);
    }

    @Override
    public ExpansionPathContainer<T> customerGroup() {
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
