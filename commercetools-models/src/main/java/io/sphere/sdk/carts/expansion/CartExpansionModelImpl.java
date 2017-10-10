package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;

import java.util.List;

final class CartExpansionModelImpl<T> extends CartLikeExpansionModelImpl<T> implements CartExpansionModel<T> {
    CartExpansionModelImpl() {
        super();
    }

    CartExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    @Override
    public CustomerGroupExpansionModel<T> customerGroup() {
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

    @Override
    public CustomLineItemExpansionModel<T> customLineItems() {
        return super.customLineItems();
    }

    @Override
    public CustomLineItemExpansionModel<T> customLineItems(final int index) {
        return super.customLineItems(index);
    }
}
