package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.queries.BooleanQueryModel;
import io.sphere.sdk.queries.QueryPredicate;

import java.util.function.Function;

public interface ProductCatalogDataQueryModel<T> extends SharedProductCatalogDataQueryModel<T> {
    @Override
    ProductDataQueryModel<T> current();

    @Override
    ProductDataQueryModel<T> forProjection(ProductProjectionType type);

    @Override
    BooleanQueryModel<T> isPublished();

    @Override
    BooleanQueryModel<T> published();

    @Override
    ProductDataQueryModel<T> staged();

    QueryPredicate<T> where(QueryPredicate<EmbeddedProductCatalogDataQueryModel> embeddedPredicate);

    QueryPredicate<T> where(Function<EmbeddedProductCatalogDataQueryModel, QueryPredicate<EmbeddedProductCatalogDataQueryModel>> embeddedPredicate);
}
