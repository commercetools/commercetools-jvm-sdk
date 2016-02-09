package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.queries.BooleanQueryModel;

interface SharedProductCatalogDataQueryModel<M> {
    ProductDataQueryModel<M> forProjection(ProductProjectionType type);

    ProductDataQueryModel<M> current();

    ProductDataQueryModel<M> staged();

    BooleanQueryModel<M> isPublished();

    BooleanQueryModel<M> published();
}
