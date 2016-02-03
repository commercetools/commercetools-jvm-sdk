package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import java.util.List;

public final class DiscountCodeInfoExpansionModel<T> extends ExpansionModel<T> {
    DiscountCodeInfoExpansionModel(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    public ExpansionPathContainer<T> discountCode() {
        return expansionPath("discountCode");
    }
}
