package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;

/**
 * Internal base interface
 * @param <T> context of the reference expansion
 */
public interface CartLikeExpansionModel<T> {
    CustomerGroupExpansionModel<T> customerGroup();

    DiscountCodeInfoExpansionModel<T> discountCodes();

    DiscountCodeInfoExpansionModel<T> discountCodes(int index);

    LineItemExpansionModel<T> lineItems();

    LineItemExpansionModel<T> lineItems(int index);

    PaymentInfoExpansionModel<T> paymentInfo();

    ShippingInfoExpansionModel<T> shippingInfo();
}
