package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;

import java.util.List;
import java.util.function.Function;

public class CartQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<CartQueryBuilder, Cart, CartQuery, CartQueryModel, CartExpansionModel<Cart>> {

    private CartQueryBuilder(final CartQuery template) {
        super(template);
    }

    public static CartQueryBuilder of() {
        return new CartQueryBuilder(CartQuery.of());
    }

    @Override
    protected CartQueryBuilder getThis() {
        return this;
    }

    @Override
    public CartQuery build() {
        return super.build();
    }

    @Override
    public CartQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public CartQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public CartQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public CartQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public CartQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public CartQueryBuilder plusExpansionPaths(final Function<CartExpansionModel<Cart>, ExpansionPathContainer<Cart>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public CartQueryBuilder plusPredicates(final Function<CartQueryModel, QueryPredicate<Cart>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public CartQueryBuilder plusPredicates(final QueryPredicate<Cart> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public CartQueryBuilder plusPredicates(final List<QueryPredicate<Cart>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public CartQueryBuilder plusSort(final Function<CartQueryModel, QuerySort<Cart>> m) {
        return super.plusSort(m);
    }

    @Override
    public CartQueryBuilder plusSort(final List<QuerySort<Cart>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public CartQueryBuilder plusSort(final QuerySort<Cart> sort) {
        return super.plusSort(sort);
    }

    @Override
    public CartQueryBuilder predicates(final Function<CartQueryModel, QueryPredicate<Cart>> m) {
        return super.predicates(m);
    }

    @Override
    public CartQueryBuilder predicates(final QueryPredicate<Cart> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public CartQueryBuilder predicates(final List<QueryPredicate<Cart>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public CartQueryBuilder sort(final Function<CartQueryModel, QuerySort<Cart>> m) {
        return super.sort(m);
    }

    @Override
    public CartQueryBuilder sort(final List<QuerySort<Cart>> sort) {
        return super.sort(sort);
    }

    @Override
    public CartQueryBuilder sort(final QuerySort<Cart> sort) {
        return super.sort(sort);
    }

    @Override
    public CartQueryBuilder sortMulti(final Function<CartQueryModel, List<QuerySort<Cart>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public CartQueryBuilder expansionPaths(final Function<CartExpansionModel<Cart>, ExpansionPathContainer<Cart>> m) {
        return super.expansionPaths(m);
    }
}
