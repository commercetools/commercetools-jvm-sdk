package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.QueryModel;

import java.util.Optional;

public class PartialProductCatalogDataQueryModel extends ProductCatalogDataQueryModel<PartialProductCatalogDataQueryModel> {
    public PartialProductCatalogDataQueryModel(final Optional<? extends QueryModel<PartialProductCatalogDataQueryModel>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public static PartialProductCatalogDataQueryModel of() {
        return new PartialProductCatalogDataQueryModel(Optional.empty(), Optional.<String>empty());
    }
}
