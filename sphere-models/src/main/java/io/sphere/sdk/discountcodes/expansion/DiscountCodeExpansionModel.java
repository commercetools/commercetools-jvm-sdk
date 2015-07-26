package io.sphere.sdk.discountcodes.expansion;

import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.expansion.ExpansionModel;

import javax.annotation.Nullable;

public class DiscountCodeExpansionModel<T> extends ExpansionModel<T> {
    public DiscountCodeExpansionModel(@Nullable final String parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    DiscountCodeExpansionModel() {
        super();
    }

    public static DiscountCodeExpansionModel<DiscountCode> of() {
        return new DiscountCodeExpansionModel<>();
    }
}
