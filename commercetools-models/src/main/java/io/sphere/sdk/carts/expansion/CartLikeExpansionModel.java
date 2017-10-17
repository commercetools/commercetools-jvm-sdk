package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.carts.ShippingRateInput;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;

/**
 * Internal base interface
 * @param <T> context of the reference expansion
 */
public interface CartLikeExpansionModel<T> extends ExpansionPathContainer<T> {
    CustomerGroupExpansionModel<T> customerGroup();

    DiscountCodeInfoExpansionModel<T> discountCodes();

    DiscountCodeInfoExpansionModel<T> discountCodes(int index);

    LineItemExpansionModel<T> lineItems();

    LineItemExpansionModel<T> lineItems(int index);

    CustomLineItemExpansionModel<T> customLineItems();

    CustomLineItemExpansionModel<T> customLineItems(int index);

    PaymentInfoExpansionModel<T> paymentInfo();

    ShippingInfoExpansionModel<T> shippingInfo();
}
