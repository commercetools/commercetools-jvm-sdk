package io.sphere.sdk.taxcategories.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.taxcategories.TaxCategory;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public final class TaxCategoryExpansionModel<T> extends ExpansionModel<T> {
    public TaxCategoryExpansionModel(final String parentPath, final String path) {
        super(parentPath, path);
    }

    TaxCategoryExpansionModel() {
        super();
    }

    public static TaxCategoryExpansionModel<TaxCategory> of() {
        return new TaxCategoryExpansionModel<>();
    }
}
