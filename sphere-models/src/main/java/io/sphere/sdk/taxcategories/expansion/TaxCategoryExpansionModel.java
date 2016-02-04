package io.sphere.sdk.taxcategories.expansion;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.taxcategories.TaxCategory;

import java.util.List;

/**
 DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public interface TaxCategoryExpansionModel<T> extends ExpansionPathContainer<T> {
    static TaxCategoryExpansionModel<TaxCategory> of() {
        return new TaxCategoryExpansionModelImpl<>();
    }

    static <T> TaxCategoryExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new TaxCategoryExpansionModelImpl<>(parentPath, path);
    }
}
