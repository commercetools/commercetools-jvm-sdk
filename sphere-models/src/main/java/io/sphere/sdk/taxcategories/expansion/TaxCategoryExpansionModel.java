package io.sphere.sdk.taxcategories.expansion;

import io.sphere.sdk.expansion.ExpandedModel;
import io.sphere.sdk.taxcategories.TaxCategory;

import java.util.List;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public final class TaxCategoryExpansionModel<T> extends ExpandedModel<T> {
    public TaxCategoryExpansionModel(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    TaxCategoryExpansionModel() {
        super();
    }

    public static TaxCategoryExpansionModel<TaxCategory> of() {
        return new TaxCategoryExpansionModel<>();
    }

    public static <T> TaxCategoryExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new TaxCategoryExpansionModel<>(parentPath, path);
    }
}
