package io.sphere.sdk.taxcategories.queries;

import io.sphere.sdk.queries.ResourceQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.taxcategories.TaxCategory;

public interface TaxCategoryQueryModel extends ResourceQueryModel<TaxCategory> {
    StringQuerySortingModel<TaxCategory> name();

    static TaxCategoryQueryModel of() {
        return new TaxCategoryQueryModelImpl(null, null);
    }
}
