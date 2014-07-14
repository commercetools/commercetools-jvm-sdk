package io.sphere.sdk.taxcategories;

import com.google.common.base.Optional;
import io.sphere.sdk.queries.*;

public class TaxCategoryQueryModel<T> extends EmbeddedQueryModel<T, TaxCategoryQueryModel<TaxCategory>> {

    private static final TaxCategoryQueryModel<TaxCategoryQueryModel<TaxCategory>> instance = new TaxCategoryQueryModel<>(Optional.<QueryModel<TaxCategoryQueryModel<TaxCategory>>>absent(), Optional.<String>absent());

    public static TaxCategoryQueryModel<TaxCategoryQueryModel<TaxCategory>> get() {
        return instance;
    }
    
    private TaxCategoryQueryModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<T> name() {
        return nameModel();
    }
}
