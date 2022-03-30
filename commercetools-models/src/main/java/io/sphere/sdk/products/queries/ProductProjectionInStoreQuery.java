package io.sphere.sdk.products.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.products.search.PriceSelectionRequestDsl;
import io.sphere.sdk.queries.MetaModelQueryDsl;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;


import java.util.List;
import java.util.function.Function;

public interface ProductProjectionInStoreQuery extends MetaModelQueryDsl<ProductProjection, ProductProjectionInStoreQuery, ProductProjectionQueryModel, ProductProjectionExpansionModel<ProductProjection>> {

    /**
     * Creates a container which contains the full Java type information to deserialize the query result (NOT this class) from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<PagedQueryResult<ProductProjection>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<ProductProjection>>() {
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<ProductProjection>>";
            }
        };
    }

    static ProductProjectionInStoreQuery of(final String storeKey) {
        return new ProductProjectionInStoreQueryImpl(storeKey);
    }

    @Override
    ProductProjectionInStoreQuery plusPredicates(final Function<ProductProjectionQueryModel, QueryPredicate<ProductProjection>> m);

    @Override
    ProductProjectionInStoreQuery plusPredicates(final QueryPredicate<ProductProjection> queryPredicate);

    @Override
    ProductProjectionInStoreQuery plusPredicates(final List<QueryPredicate<ProductProjection>> queryPredicates);

    @Override
    ProductProjectionInStoreQuery plusSort(final Function<ProductProjectionQueryModel, QuerySort<ProductProjection>> m);

    @Override
    ProductProjectionInStoreQuery plusSort(final List<QuerySort<ProductProjection>> sort);

    @Override
    ProductProjectionInStoreQuery plusSort(final QuerySort<ProductProjection> sort);

    @Override
    ProductProjectionInStoreQuery withPredicates(final Function<ProductProjectionQueryModel, QueryPredicate<ProductProjection>> predicateFunction);

    @Override
    ProductProjectionInStoreQuery withPredicates(final QueryPredicate<ProductProjection> queryPredicate);

    @Override
    ProductProjectionInStoreQuery withPredicates(final List<QueryPredicate<ProductProjection>> queryPredicates);

    @Override
    ProductProjectionInStoreQuery withSort(final Function<ProductProjectionQueryModel, QuerySort<ProductProjection>> m);

    @Override
    ProductProjectionInStoreQuery withSort(final List<QuerySort<ProductProjection>> sort);

    @Override
    ProductProjectionInStoreQuery withSort(final QuerySort<ProductProjection> sort);

    @Override
    ProductProjectionInStoreQuery withSortMulti(final Function<ProductProjectionQueryModel, List<QuerySort<ProductProjection>>> m);

    @Override
    ProductProjectionInStoreQuery plusExpansionPaths(final Function<ProductProjectionExpansionModel<ProductProjection>, ExpansionPathContainer<ProductProjection>> m);

    @Override
    ProductProjectionInStoreQuery withExpansionPaths(final Function<ProductProjectionExpansionModel<ProductProjection>, ExpansionPathContainer<ProductProjection>> m);

    @Override
    ProductProjectionInStoreQuery withFetchTotal(final boolean fetchTotal);

    @Override
    ProductProjectionInStoreQuery withLimit(final Long limit);

    @Override
    ProductProjectionInStoreQuery withOffset(final Long offset);
    
}
