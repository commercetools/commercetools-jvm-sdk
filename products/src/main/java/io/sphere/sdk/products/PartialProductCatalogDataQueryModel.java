package io.sphere.sdk.products;

import io.sphere.sdk.queries.QueryModel;

import java.util.Optional;

public class PartialProductCatalogDataQueryModel extends ProductCatalogDataQueryModel<PartialProductCatalogDataQueryModel> {
    public PartialProductCatalogDataQueryModel(final Optional<? extends QueryModel<PartialProductCatalogDataQueryModel>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }
}
