package io.sphere.sdk.taxcategories.expansion;

import io.sphere.sdk.expansion.ExpandedModel;

import java.util.List;

final class TaxCategoryExpansionModelImpl<T> extends ExpandedModel<T> implements TaxCategoryExpansionModel<T> {
    public TaxCategoryExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    TaxCategoryExpansionModelImpl() {
        super();
    }
}
