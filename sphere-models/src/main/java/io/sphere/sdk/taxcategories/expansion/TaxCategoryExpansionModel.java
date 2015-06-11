package io.sphere.sdk.taxcategories.expansion;

import io.sphere.sdk.queries.ExpansionModel;
import io.sphere.sdk.taxcategories.TaxCategory;

import java.util.Optional;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public class TaxCategoryExpansionModel<T> extends ExpansionModel<T> {
    public TaxCategoryExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    TaxCategoryExpansionModel() {
        super();
    }

    public static TaxCategoryExpansionModel<TaxCategory> of() {
        return new TaxCategoryExpansionModel<>();
    }
}
