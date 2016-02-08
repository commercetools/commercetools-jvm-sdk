package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.QueryModel;

import javax.annotation.Nullable;

final class PartialProductCatalogDataQueryModelImpl extends ProductCatalogDataQueryModelImpl<PartialProductCatalogDataQueryModel> implements PartialProductCatalogDataQueryModel {
    PartialProductCatalogDataQueryModelImpl(@Nullable final QueryModel<PartialProductCatalogDataQueryModel> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }
}
