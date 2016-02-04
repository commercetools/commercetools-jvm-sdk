package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;

/**
 * Model to explore reference fields in a cart that can be expanded.
 *
 * @param <T> the context type
 */
public interface CartExpansionModel<T> extends CartLikeExpansionModel<T> {
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

    static CartExpansionModel<Cart> of() {
        return new CartExpansionModelImpl<>();
    }
}
