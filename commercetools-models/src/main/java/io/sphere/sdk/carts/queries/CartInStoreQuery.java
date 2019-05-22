package io.sphere.sdk.carts.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.MetaModelQueryDsl;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;

import java.util.List;
import java.util.function.Function;

public interface CartInStoreQuery extends MetaModelQueryDsl<Cart, CartInStoreQuery, CartQueryModel, CartExpansionModel<Cart>> {

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
    static TypeReference<PagedQueryResult<Cart>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Cart>>() {
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Cart>>";
            }
        };
    }

    static CartInStoreQuery of(final String storeKey) {
        return new CartInStoreQueryImpl(storeKey);
    }

    @Override
    CartInStoreQuery plusPredicates(final Function<CartQueryModel, QueryPredicate<Cart>> m);

    @Override
    CartInStoreQuery plusPredicates(final QueryPredicate<Cart> queryPredicate);

    @Override
    CartInStoreQuery plusPredicates(final List<QueryPredicate<Cart>> queryPredicates);

    @Override
    CartInStoreQuery plusSort(final Function<CartQueryModel, QuerySort<Cart>> m);

    @Override
    CartInStoreQuery plusSort(final List<QuerySort<Cart>> sort);

    @Override
    CartInStoreQuery plusSort(final QuerySort<Cart> sort);

    @Override
    CartInStoreQuery withPredicates(final Function<CartQueryModel, QueryPredicate<Cart>> predicateFunction);

    @Override
    CartInStoreQuery withPredicates(final QueryPredicate<Cart> queryPredicate);

    @Override
    CartInStoreQuery withPredicates(final List<QueryPredicate<Cart>> queryPredicates);

    @Override
    CartInStoreQuery withSort(final Function<CartQueryModel, QuerySort<Cart>> m);

    @Override
    CartInStoreQuery withSort(final List<QuerySort<Cart>> sort);

    @Override
    CartInStoreQuery withSort(final QuerySort<Cart> sort);

    @Override
    CartInStoreQuery withSortMulti(final Function<CartQueryModel, List<QuerySort<Cart>>> m);

    @Override
    CartInStoreQuery plusExpansionPaths(final Function<CartExpansionModel<Cart>, ExpansionPathContainer<Cart>> m);

    @Override
    CartInStoreQuery withExpansionPaths(final Function<CartExpansionModel<Cart>, ExpansionPathContainer<Cart>> m);

    @Override
    CartInStoreQuery withFetchTotal(final boolean fetchTotal);

    @Override
    CartInStoreQuery withLimit(final Long limit);

    @Override
    CartInStoreQuery withOffset(final Long offset);

}
