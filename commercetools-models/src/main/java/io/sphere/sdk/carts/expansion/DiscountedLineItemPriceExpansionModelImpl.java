package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;

import java.util.List;

final class DiscountedLineItemPriceExpansionModelImpl<T> extends ExpansionModelImpl<T> implements DiscountedLineItemPriceExpansionModel<T> {
    DiscountedLineItemPriceExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    @Override
    public DiscountedLineItemPortionExpansionModel<T> includedDiscounts() {
        return new DiscountedLineItemPortionExpansionModelImpl<>(buildPathExpression(), "includedDiscounts[*]");
    }
}
