package io.sphere.sdk.discountcodes.expansion;

import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.queries.ExpansionModel;

import java.util.Optional;

public class DiscountCodeExpansionModel<T> extends ExpansionModel<T> {
    public DiscountCodeExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    DiscountCodeExpansionModel() {
        super();
    }

    public static DiscountCodeExpansionModel<DiscountCode> of() {
        return new DiscountCodeExpansionModel<>();
    }
}
