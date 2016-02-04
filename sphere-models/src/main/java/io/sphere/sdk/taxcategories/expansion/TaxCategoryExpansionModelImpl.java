package io.sphere.sdk.taxcategories.expansion;

import io.sphere.sdk.expansion.ExpansionModel;

import java.util.List;

final class TaxCategoryExpansionModelImpl<T> extends ExpansionModel<T> implements TaxCategoryExpansionModel<T> {
    public TaxCategoryExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    TaxCategoryExpansionModelImpl() {
        super();
    }
}
