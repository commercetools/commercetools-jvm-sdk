package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;

import java.util.List;

final class DiscountedLineItemPricePerQuantityExpansionModelImpl<T> extends ExpansionModelImpl<T> implements DiscountedLineItemPricePerQuantityExpansionModel<T> {
    public DiscountedLineItemPricePerQuantityExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    @Override
    public DiscountedLineItemPriceExpansionModel<T> discountedPrice() {
        return DiscountedLineItemPriceExpansionModel.of(buildPathExpression(), "discountedPrice");
    }
}
