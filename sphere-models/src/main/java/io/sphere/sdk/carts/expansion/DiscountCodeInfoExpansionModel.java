package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ReferenceExpansionSupport;

import java.util.List;

public class DiscountCodeInfoExpansionModel<T> extends ExpansionModel<T> {
    DiscountCodeInfoExpansionModel(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    public ReferenceExpansionSupport<T> discountCode() {
        return expansionPath("discountCode");
    }
}
