package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.*;

public class CartQueryModel extends CartLikeQueryModel<Cart> {

    public static CartQueryModel of() {
        return new CartQueryModel(null, null);
    }

    private CartQueryModel(QueryModel<Cart> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public CountryQueryModel<Cart> country() {
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
}
