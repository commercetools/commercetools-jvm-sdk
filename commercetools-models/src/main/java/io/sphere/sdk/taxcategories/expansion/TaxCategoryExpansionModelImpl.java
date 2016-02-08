package io.sphere.sdk.taxcategories.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;

import java.util.List;

final class TaxCategoryExpansionModelImpl<T> extends ExpansionModelImpl<T> implements TaxCategoryExpansionModel<T> {
    public TaxCategoryExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    TaxCategoryExpansionModelImpl() {
        super();
    }
}
