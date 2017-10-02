package io.sphere.sdk.carts.queries;

import io.sphere.sdk.queries.*;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.taxcategories.queries.TaxRateQueryModel;

final class CartShippingInfoQueryModelImpl<T> extends QueryModelImpl<T> implements CartShippingInfoQueryModel<T> {
    public CartShippingInfoQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public DiscountedLineItemPriceQueryModel<T> discountedPrice() {
        return new DiscountedLineItemPriceQueryModelImpl<>(this, "discountedPrice");
    }

    @Override
    public StringQueryModel<T> shippingMethodName() {
        return stringModel("shippingMethodName");
    }

    @Override
    public MoneyQueryModel<T> price() {
        return moneyModel("price");
    }

    @Override
    public TaxRateQueryModel<T> taxRate() {
        return new TaxRateQueryModelImpl<>(this, "taxRate");
    }

    @Override
    public ReferenceOptionalQueryModel<T, ShippingMethod> shippingMethod() {
        return referenceOptionalModel("shippingMethod");
    }

}
