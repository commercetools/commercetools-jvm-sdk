package io.sphere.sdk.shoppinglists.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.MetaModelQueryDsl;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.expansion.ShoppingListExpansionModel;

import java.util.List;
import java.util.function.Function;

public interface ShoppingListInStoreQuery extends MetaModelQueryDsl<ShoppingList, ShoppingListInStoreQuery, ShoppingListQueryModel, ShoppingListExpansionModel<ShoppingList>> {

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
    static TypeReference<PagedQueryResult<ShoppingList>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<ShoppingList>>() {
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<ShoppingList>>";
            }
        };
    }

    static ShoppingListInStoreQuery of(final String storeKey) {
        return new ShoppingListInStoreQueryImpl(storeKey);
    }

    @Override
    ShoppingListInStoreQuery plusPredicates(final Function<ShoppingListQueryModel, QueryPredicate<ShoppingList>> m);

    @Override
    ShoppingListInStoreQuery plusPredicates(final QueryPredicate<ShoppingList> queryPredicate);

    @Override
    ShoppingListInStoreQuery plusPredicates(final List<QueryPredicate<ShoppingList>> queryPredicates);

    @Override
    ShoppingListInStoreQuery plusSort(final Function<ShoppingListQueryModel, QuerySort<ShoppingList>> m);

    @Override
    ShoppingListInStoreQuery plusSort(final List<QuerySort<ShoppingList>> sort);

    @Override
    ShoppingListInStoreQuery plusSort(final QuerySort<ShoppingList> sort);

    @Override
    ShoppingListInStoreQuery withPredicates(final Function<ShoppingListQueryModel, QueryPredicate<ShoppingList>> predicateFunction);

    @Override
    ShoppingListInStoreQuery withPredicates(final QueryPredicate<ShoppingList> queryPredicate);

    @Override
    ShoppingListInStoreQuery withPredicates(final List<QueryPredicate<ShoppingList>> queryPredicates);

    @Override
    ShoppingListInStoreQuery withSort(final Function<ShoppingListQueryModel, QuerySort<ShoppingList>> m);

    @Override
    ShoppingListInStoreQuery withSort(final List<QuerySort<ShoppingList>> sort);

    @Override
    ShoppingListInStoreQuery withSort(final QuerySort<ShoppingList> sort);

    @Override
    ShoppingListInStoreQuery withSortMulti(final Function<ShoppingListQueryModel, List<QuerySort<ShoppingList>>> m);

    @Override
    ShoppingListInStoreQuery plusExpansionPaths(final Function<ShoppingListExpansionModel<ShoppingList>, ExpansionPathContainer<ShoppingList>> m);

    @Override
    ShoppingListInStoreQuery withExpansionPaths(final Function<ShoppingListExpansionModel<ShoppingList>, ExpansionPathContainer<ShoppingList>> m);

    @Override
    ShoppingListInStoreQuery withFetchTotal(final boolean fetchTotal);

    @Override
    ShoppingListInStoreQuery withLimit(final Long limit);

    @Override
    ShoppingListInStoreQuery withOffset(final Long offset);
    
}
