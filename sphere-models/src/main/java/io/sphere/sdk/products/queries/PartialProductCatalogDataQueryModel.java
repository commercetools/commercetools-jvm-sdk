package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.QueryModel;

import javax.annotation.Nullable;

public class PartialProductCatalogDataQueryModel extends ProductCatalogDataQueryModelImpl<PartialProductCatalogDataQueryModel> {
    private PartialProductCatalogDataQueryModel(@Nullable final QueryModel<PartialProductCatalogDataQueryModel> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public static PartialProductCatalogDataQueryModel of() {
        return new PartialProductCatalogDataQueryModel(null, null);
    }
}
