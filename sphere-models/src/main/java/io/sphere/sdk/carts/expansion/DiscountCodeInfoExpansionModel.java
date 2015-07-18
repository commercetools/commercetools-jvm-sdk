package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;

import java.util.Optional;

public class DiscountCodeInfoExpansionModel<T> extends ExpansionModel<T> {
    DiscountCodeInfoExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    public ExpansionPath<T> discountCode() {
        return expansionPath("discountCode");
    }
}
