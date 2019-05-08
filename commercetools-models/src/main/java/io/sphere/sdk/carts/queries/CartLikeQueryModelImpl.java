package io.sphere.sdk.carts.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.types.queries.CustomResourceQueryModelImpl;

/**
 * Base class to create predicates for {@link io.sphere.sdk.carts.CartLike} resources.
 * @param <T> query context
 */
public abstract class CartLikeQueryModelImpl<T> extends CustomResourceQueryModelImpl<T> implements CartLikeQueryModel<T> {
    protected CartLikeQueryModelImpl(QueryModel<T> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQuerySortingModel<T> customerId() {
        return stringModel("customerId");
    }

    @Override
    public StringQuerySortingModel<T> customerEmail() {
        return stringModel("customerEmail");
    }

    @Override
    public MoneyQueryModel<T> totalPrice() {
        return moneyModel("totalPrice");
    }

    @Override
    public TaxedPriceOptionalQueryModel<T> taxedPrice() {
        return new TaxedPriceOptionalQueryModelImpl<>(this, "taxedPrice");
    }

    @Override
    public CountryQueryModel<T> country() {
        return countryQueryModel("country");
    }

    @Override
    public ReferenceOptionalQueryModel<T, CustomerGroup> customerGroup() {
        return referenceOptionalModel("customerGroup");
    }

    @Override
    public LineItemCollectionQueryModel<T> lineItems() {
        return new LineItemLikeCollectionQueryModelImpl<>(this, "lineItems");
    }

    @Override
    public CustomLineItemCollectionQueryModel<T> customLineItems() {
        return new LineItemLikeCollectionQueryModelImpl<>(this, "customLineItems");
    }

    @Override
    public AddressQueryModel<T> shippingAddress() {
        return addressModel("shippingAddress");
    }

    @Override
    public AddressQueryModel<T> billingAddress() {
        return addressModel("billingAddress");
    }

    @Override
    public CartShippingInfoQueryModel<T> shippingInfo() {
        return new CartShippingInfoQueryModelImpl<>(this, "shippingInfo");
    }

    @Override
    public DiscountCodeInfoCollectionQueryModel<T> discountCodes() {
        return new DiscountCodeInfoCollectionQueryModelImpl<>(this, "discountCodes");
    }

    @Override
    public PaymentInfoQueryModel<T> paymentInfo() {
        return new PaymentInfoQueryModelImpl<>(this, "paymentInfo");
    }

    @Override
    public ShippingRateInputQueryModel<T> shippingRateInput() {
        return new ShippingRateInputQueryModelImpl<>(this,"shippingRateInput");
    }

    @Override
    public StringQuerySortingModel<T> anonymousId() {
        return stringModel("anonymousId");
    }

    @Override
    public LocaleQueryModel<T> locale() {
        return localeQuerySortingModel("locale");
    }

    @Override
    public AddressCollectionQueryModel<T> itemShippingAddresses() {
        return addressCollectionQueryModel("itemShippingAddresses");
    }
}