package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.discountcodes.expansion.DiscountCodeExpansionModel;
import io.sphere.sdk.expansion.ExpansionModelImpl;

import java.util.List;

final class DiscountCodeInfoExpansionModelImpl<T> extends ExpansionModelImpl<T> implements DiscountCodeInfoExpansionModel<T> {
    DiscountCodeInfoExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    @Override
    public DiscountCodeExpansionModel<T> discountCode() {
        return DiscountCodeExpansionModel.of(buildPathExpression(), "discountCode");
    }
}
