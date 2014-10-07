package io.sphere.sdk.taxcategories.queries;

import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.taxcategories.TaxCategory;

import java.util.Optional;

public class TaxCategoryQueryModel extends QueryModelImpl<TaxCategory> {

    private static final TaxCategoryQueryModel instance = new TaxCategoryQueryModel(Optional.<QueryModel<TaxCategory>>empty(), Optional.<String>empty());

    static TaxCategoryQueryModel get() {
        return instance;
    }

    private TaxCategoryQueryModel(final Optional<? extends QueryModel<TaxCategory>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<TaxCategory> name() {
        return new StringQuerySortingModel<>(Optional.of(this), "name");
    }
}
