package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;

import java.util.List;
import java.util.function.Function;

public final class CartInStoreQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<CartInStoreQueryBuilder, Cart, CartInStoreQuery, CartQueryModel, CartExpansionModel<Cart>> {
    
    private CartInStoreQueryBuilder(final CartInStoreQuery template) {
        super(template);
    }

    public static CartInStoreQueryBuilder of(final String storeKey) {
        return new CartInStoreQueryBuilder(CartInStoreQuery.of(storeKey));
    }

    @Override
    protected CartInStoreQueryBuilder getThis() {
        return this;
    }

    @Override
    public CartInStoreQuery build() {
        return super.build();
    }

    @Override
    public CartInStoreQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public CartInStoreQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public CartInStoreQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public CartInStoreQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public CartInStoreQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public CartInStoreQueryBuilder plusExpansionPaths(final Function<CartExpansionModel<Cart>, ExpansionPathContainer<Cart>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public CartInStoreQueryBuilder plusPredicates(final Function<CartQueryModel, QueryPredicate<Cart>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public CartInStoreQueryBuilder plusPredicates(final QueryPredicate<Cart> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public CartInStoreQueryBuilder plusPredicates(final List<QueryPredicate<Cart>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public CartInStoreQueryBuilder plusSort(final Function<CartQueryModel, QuerySort<Cart>> m) {
        return super.plusSort(m);
    }

    @Override
    public CartInStoreQueryBuilder plusSort(final List<QuerySort<Cart>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public CartInStoreQueryBuilder plusSort(final QuerySort<Cart> sort) {
        return super.plusSort(sort);
    }

    @Override
    public CartInStoreQueryBuilder predicates(final Function<CartQueryModel, QueryPredicate<Cart>> m) {
        return super.predicates(m);
    }

    @Override
    public CartInStoreQueryBuilder predicates(final QueryPredicate<Cart> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public CartInStoreQueryBuilder predicates(final List<QueryPredicate<Cart>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public CartInStoreQueryBuilder sort(final Function<CartQueryModel, QuerySort<Cart>> m) {
        return super.sort(m);
    }

    @Override
    public CartInStoreQueryBuilder sort(final List<QuerySort<Cart>> sort) {
        return super.sort(sort);
    }

    @Override
    public CartInStoreQueryBuilder sort(final QuerySort<Cart> sort) {
        return super.sort(sort);
    }

    @Override
    public CartInStoreQueryBuilder sortMulti(final Function<CartQueryModel, List<QuerySort<Cart>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public CartInStoreQueryBuilder expansionPaths(final Function<CartExpansionModel<Cart>, ExpansionPathContainer<Cart>> m) {
        return super.expansionPaths(m);
    }
}