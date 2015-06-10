package io.sphere.sdk.taxcategories.queries;

import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.taxcategories.TaxCategory;

import java.util.Optional;

public class TaxCategoryQueryModel extends DefaultModelQueryModelImpl<TaxCategory> {

    public static TaxCategoryQueryModel of() {
        return new TaxCategoryQueryModel(Optional.empty(), Optional.empty());
    }

    private TaxCategoryQueryModel(final Optional<? extends QueryModel<TaxCategory>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<TaxCategory> name() {
        return new StringQuerySortingModel<>(Optional.of(this), "name");
    }
}
