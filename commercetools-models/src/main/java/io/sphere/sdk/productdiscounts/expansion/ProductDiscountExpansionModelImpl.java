package io.sphere.sdk.productdiscounts.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import java.util.List;

final class ProductDiscountExpansionModelImpl<T> extends ExpansionModelImpl<T> implements ProductDiscountExpansionModel<T> {
    ProductDiscountExpansionModelImpl() {
    }

    ProductDiscountExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    @Override
    public ExpansionPathContainer<T> references() {
        return expansionPath("references[*]");
    }
}
