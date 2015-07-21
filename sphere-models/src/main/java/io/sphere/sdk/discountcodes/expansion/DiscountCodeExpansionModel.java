package io.sphere.sdk.discountcodes.expansion;

import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.expansion.ExpansionModel;

public class DiscountCodeExpansionModel<T> extends ExpansionModel<T> {
    public DiscountCodeExpansionModel(final String parentPath, final String path) {
        super(parentPath, path);
    }

    DiscountCodeExpansionModel() {
        super();
    }

    public static DiscountCodeExpansionModel<DiscountCode> of() {
        return new DiscountCodeExpansionModel<>();
    }
}
