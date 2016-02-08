package io.sphere.sdk.taxcategories.queries;

import io.sphere.sdk.queries.ResourceQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.taxcategories.TaxCategory;

final class TaxCategoryQueryModelImpl extends ResourceQueryModelImpl<TaxCategory> implements TaxCategoryQueryModel {

    TaxCategoryQueryModelImpl(final QueryModel<TaxCategory> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQuerySortingModel<TaxCategory> name() {
        return stringModel("name");
    }
}
