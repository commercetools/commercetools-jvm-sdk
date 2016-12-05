package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartState;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.*;

final class CartQueryModelImpl extends CartLikeQueryModelImpl<Cart> implements CartQueryModel {

    CartQueryModelImpl(QueryModel<Cart> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public CountryQueryModel <Cart> country() {
        return super.country();
    }

    @Override
    public StringQuerySortingModel<Cart> customerEmail() {
        return super.customerEmail();
    }

    @Override
    public ReferenceOptionalQueryModel<Cart, CustomerGroup> customerGroup() {
        return super.customerGroup();
    }

    @Override
    public StringQuerySortingModel<Cart> customerId() {
        return super.customerId();
    }

    @Override
    public LineItemCollectionQueryModel<Cart> lineItems() {
        return super.lineItems();
    }

    @Override
    public TaxedPriceOptionalQueryModel<Cart> taxedPrice() {
        return super.taxedPrice();
    }

    @Override
    public MoneyQueryModel<Cart> totalPrice() {
        return super.totalPrice();
    }

    @Override
    public CustomLineItemCollectionQueryModel<Cart> customLineItems() {
        return super.customLineItems();
    }

    @Override
    public AddressQueryModel<Cart> billingAddress() {
        return super.billingAddress();
    }

    @Override
    public DiscountCodeInfoCollectionQueryModel<Cart> discountCodes() {
        return super.discountCodes();
    }

    @Override
    public AddressQueryModel<Cart> shippingAddress() {
        return super.shippingAddress();
    }

    @Override
    public CartShippingInfoQueryModel<Cart> shippingInfo() {
        return super.shippingInfo();
    }

    @Override
    public SphereEnumerationQueryModel<Cart, CartState> cartState() {
        return enumerationQueryModel("cartState");
    }
}
