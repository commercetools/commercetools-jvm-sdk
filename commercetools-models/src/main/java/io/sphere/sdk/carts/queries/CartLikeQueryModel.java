package io.sphere.sdk.carts.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.types.queries.WithCustomQueryModel;

public interface CartLikeQueryModel<T> extends ResourceQueryModel<T>, WithCustomQueryModel<T> {
    StringQuerySortingModel<T> customerId();

    StringQuerySortingModel<T> customerEmail();

    MoneyQueryModel<T> totalPrice();

    TaxedPriceOptionalQueryModel<T> taxedPrice();

    CountryQueryModel<T> country();

    ReferenceOptionalQueryModel<T, CustomerGroup> customerGroup();

    LineItemCollectionQueryModel<T> lineItems();

    CustomLineItemCollectionQueryModel<T> customLineItems();

    AddressQueryModel<T> shippingAddress();

    AddressQueryModel<T> billingAddress();

    CartShippingInfoQueryModel<T> shippingInfo();

    DiscountCodeInfoCollectionQueryModel<T> discountCodes();

    PaymentInfoQueryModel<T> paymentInfo();

    ShippingRateInputQueryModel<T> shippingRateInput();

    StringQuerySortingModel<T> anonymousId();

    LocaleQueryModel<T> locale();

    AddressCollectionQueryModel<T> itemShippingAddresses();

    KeyReferenceQueryModel<T> store();
}
