package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;

import java.util.List;

final class CustomLineItemExpansionModelImpl<T> extends ExpansionModelImpl<T> implements CustomLineItemExpansionModel<T> {
    CustomLineItemExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    @Override
    public DiscountedLineItemPricePerQuantityExpansionModel<T> discountedPricePerQuantity() {
        return discountedPricePerQuantity("*");
    }

    @Override
    public DiscountedLineItemPricePerQuantityExpansionModel<T> discountedPricePerQuantity(final int index) {
        return discountedPricePerQuantity("" + index);
    }

    private DiscountedLineItemPricePerQuantityExpansionModel<T> discountedPricePerQuantity(final String s) {
        return new DiscountedLineItemPricePerQuantityExpansionModelImpl<>(pathExpression(), "discountedPricePerQuantity[" + s + "]");
    }
}
