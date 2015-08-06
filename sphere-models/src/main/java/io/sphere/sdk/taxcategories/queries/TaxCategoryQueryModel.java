package io.sphere.sdk.taxcategories.queries;

import io.sphere.sdk.queries.ResourceQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.taxcategories.TaxCategory;

public class TaxCategoryQueryModel extends ResourceQueryModelImpl<TaxCategory> {

    public static TaxCategoryQueryModel of() {
        return new TaxCategoryQueryModel(null, null);
    }

    private TaxCategoryQueryModel(final QueryModel<TaxCategory> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<TaxCategory> name() {
        return stringModel("name");
    }
}
