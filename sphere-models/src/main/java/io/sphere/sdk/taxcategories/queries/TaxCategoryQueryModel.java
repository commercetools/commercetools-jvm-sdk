package io.sphere.sdk.taxcategories.queries;

import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.taxcategories.TaxCategory;

import java.util.Optional;

public class TaxCategoryQueryModel<T> extends DefaultModelQueryModelImpl<T> {

    public static TaxCategoryQueryModel<TaxCategory> of() {
        return new TaxCategoryQueryModel<>(Optional.empty(), Optional.empty());
    }

    private TaxCategoryQueryModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<T> name() {
        return new StringQuerySortingModel<>(Optional.of(this), "name");
    }
}
