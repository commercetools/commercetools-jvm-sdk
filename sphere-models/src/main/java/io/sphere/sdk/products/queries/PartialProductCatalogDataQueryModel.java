package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.QueryModel;

public class PartialProductCatalogDataQueryModel extends ProductCatalogDataQueryModel<PartialProductCatalogDataQueryModel> {
    private PartialProductCatalogDataQueryModel(final QueryModel<PartialProductCatalogDataQueryModel> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public static PartialProductCatalogDataQueryModel of() {
        return new PartialProductCatalogDataQueryModel(null, null);
    }
}
