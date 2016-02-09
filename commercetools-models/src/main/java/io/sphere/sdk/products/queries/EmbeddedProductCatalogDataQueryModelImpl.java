package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.QueryModel;

import javax.annotation.Nullable;

final class EmbeddedProductCatalogDataQueryModelImpl extends ProductCatalogDataQueryModelImpl<EmbeddedProductCatalogDataQueryModel> implements EmbeddedProductCatalogDataQueryModel {
    EmbeddedProductCatalogDataQueryModelImpl(@Nullable final QueryModel<EmbeddedProductCatalogDataQueryModel> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }
}
