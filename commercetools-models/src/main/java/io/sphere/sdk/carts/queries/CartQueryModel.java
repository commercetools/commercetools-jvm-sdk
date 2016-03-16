package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartState;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.types.queries.WithCustomQueryModel;

public interface CartQueryModel extends ResourceQueryModel<Cart>, WithCustomQueryModel<Cart>, CartLikeQueryModel<Cart> {
    CountryQueryModel<Cart> country();

    StringQuerySortingModel<Cart> customerEmail();

    ReferenceOptionalQueryModel<Cart, CustomerGroup> customerGroup();

    StringQuerySortingModel<Cart> customerId();

    LineItemCollectionQueryModel<Cart> lineItems();

    TaxedPriceOptionalQueryModel<Cart> taxedPrice();

    MoneyQueryModel<Cart> totalPrice();

    CustomLineItemCollectionQueryModel<Cart> customLineItems();

    AddressQueryModel<Cart> billingAddress();

    DiscountCodeInfoCollectionQueryModel<Cart> discountCodes();

    AddressQueryModel<Cart> shippingAddress();

    CartShippingInfoQueryModel<Cart> shippingInfo();

    @Override
    PaymentInfoQueryModel<Cart> paymentInfo();

    SphereEnumerationQueryModel<Cart, CartState> cartState();

    static CartQueryModel of() {
        return new CartQueryModelImpl(null, null);
    }
}
