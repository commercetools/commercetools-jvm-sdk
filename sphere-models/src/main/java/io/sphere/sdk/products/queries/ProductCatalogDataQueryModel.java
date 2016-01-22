package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.queries.BooleanQueryModel;
import io.sphere.sdk.queries.QueryPredicate;

import java.util.function.Function;

public interface ProductCatalogDataQueryModel<M> {
    ProductDataQueryModel<M> forProjection(ProductProjectionType type);

    ProductDataQueryModel<M> current();

    ProductDataQueryModel<M> staged();

    BooleanQueryModel<M> isPublished();

    BooleanQueryModel<M> published();

    QueryPredicate<M> where(QueryPredicate<PartialProductCatalogDataQueryModel> embeddedPredicate);

    QueryPredicate<M> where(Function<PartialProductCatalogDataQueryModel, QueryPredicate<PartialProductCatalogDataQueryModel>> embeddedPredicate);
}
