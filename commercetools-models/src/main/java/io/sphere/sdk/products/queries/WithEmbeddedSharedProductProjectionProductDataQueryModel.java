package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.QueryPredicate;

import java.util.function.Function;

/**
 * internal interface for queryable fields which are in {@link io.sphere.sdk.products.ProductProjection} AND {@link io.sphere.sdk.products.ProductData}.
 *
 * @param <T> context type
 */
interface WithEmbeddedSharedProductProjectionProductDataQueryModel<T> extends SharedProductProjectionProductDataQueryModel<T> {
    QueryPredicate<T> where(QueryPredicate<EmbeddedProductDataQueryModel> embeddedPredicate);

    QueryPredicate<T> where(Function<EmbeddedProductDataQueryModel, QueryPredicate<EmbeddedProductDataQueryModel>> embeddedPredicate);
}
