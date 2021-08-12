package io.sphere.sdk.shoppinglists.queries;

import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;
import io.sphere.sdk.shoppinglists.expansion.ShoppingListExpansionModel;

import java.util.List;
import java.util.function.Function;

public final class ShoppingListInStoreQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<ShoppingListInStoreQueryBuilder, ShoppingList, ShoppingListInStoreQuery, ShoppingListQueryModel, ShoppingListExpansionModel<ShoppingList>> {

    private ShoppingListInStoreQueryBuilder(final ShoppingListInStoreQuery template) {
        super(template);
    }

    public static ShoppingListInStoreQueryBuilder of(final String storeKey) {
        return new ShoppingListInStoreQueryBuilder(ShoppingListInStoreQuery.of(storeKey));
    }

    @Override
    protected ShoppingListInStoreQueryBuilder getThis() {
        return this;
    }

    @Override
    public ShoppingListInStoreQuery build() {
        return super.build();
    }

    @Override
    public ShoppingListInStoreQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public ShoppingListInStoreQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public ShoppingListInStoreQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public ShoppingListInStoreQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public ShoppingListInStoreQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public ShoppingListInStoreQueryBuilder plusExpansionPaths(final Function<ShoppingListExpansionModel<ShoppingList>, ExpansionPathContainer<ShoppingList>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public ShoppingListInStoreQueryBuilder plusPredicates(final Function<ShoppingListQueryModel, QueryPredicate<ShoppingList>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public ShoppingListInStoreQueryBuilder plusPredicates(final QueryPredicate<ShoppingList> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public ShoppingListInStoreQueryBuilder plusPredicates(final List<QueryPredicate<ShoppingList>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public ShoppingListInStoreQueryBuilder plusSort(final Function<ShoppingListQueryModel, QuerySort<ShoppingList>> m) {
        return super.plusSort(m);
    }

    @Override
    public ShoppingListInStoreQueryBuilder plusSort(final List<QuerySort<ShoppingList>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ShoppingListInStoreQueryBuilder plusSort(final QuerySort<ShoppingList> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ShoppingListInStoreQueryBuilder predicates(final Function<ShoppingListQueryModel, QueryPredicate<ShoppingList>> m) {
        return super.predicates(m);
    }

    @Override
    public ShoppingListInStoreQueryBuilder predicates(final QueryPredicate<ShoppingList> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public ShoppingListInStoreQueryBuilder predicates(final List<QueryPredicate<ShoppingList>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public ShoppingListInStoreQueryBuilder sort(final Function<ShoppingListQueryModel, QuerySort<ShoppingList>> m) {
        return super.sort(m);
    }

    @Override
    public ShoppingListInStoreQueryBuilder sort(final List<QuerySort<ShoppingList>> sort) {
        return super.sort(sort);
    }

    @Override
    public ShoppingListInStoreQueryBuilder sort(final QuerySort<ShoppingList> sort) {
        return super.sort(sort);
    }

    @Override
    public ShoppingListInStoreQueryBuilder sortMulti(final Function<ShoppingListQueryModel, List<QuerySort<ShoppingList>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public ShoppingListInStoreQueryBuilder expansionPaths(final Function<ShoppingListExpansionModel<ShoppingList>, ExpansionPathContainer<ShoppingList>> m) {
        return super.expansionPaths(m);
    }
    
}
