package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.discountcodes.expansion.DiscountCodeExpansionModel;
import io.sphere.sdk.expansion.ExpansionModel;

import java.util.List;

public final class DiscountCodeInfoExpansionModel<T> extends ExpansionModel<T> {
    DiscountCodeInfoExpansionModel(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    public DiscountCodeExpansionModel<T> discountCode() {
        return DiscountCodeExpansionModel.of(buildPathExpression(), "discountCode");
    }
}
