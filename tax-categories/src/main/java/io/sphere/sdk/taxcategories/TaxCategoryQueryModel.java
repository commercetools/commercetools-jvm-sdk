package io.sphere.sdk.taxcategories;

import java.util.Optional;
import io.sphere.sdk.queries.*;

public class TaxCategoryQueryModel<T> extends EmbeddedQueryModel<T, TaxCategoryQueryModel<TaxCategory>> {

    private static final TaxCategoryQueryModel<TaxCategoryQueryModel<TaxCategory>> instance = new TaxCategoryQueryModel<>(Optional.<QueryModel<TaxCategoryQueryModel<TaxCategory>>>empty(), Optional.<String>empty());

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
