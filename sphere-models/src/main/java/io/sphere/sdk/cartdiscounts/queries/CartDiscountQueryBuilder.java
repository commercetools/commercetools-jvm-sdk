package io.sphere.sdk.cartdiscounts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;

import java.util.List;
import java.util.function.Function;

/**

 {@doc.gen summary cart discounts}

 */
public class CartDiscountQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<CartDiscountQueryBuilder, CartDiscount, CartDiscountQuery, CartDiscountQueryModel, CartDiscountExpansionModel<CartDiscount>> {

    private CartDiscountQueryBuilder(final CartDiscountQuery template) {
        super(template);
    }

    public static CartDiscountQueryBuilder of() {
        return new CartDiscountQueryBuilder(CartDiscountQuery.of());
    }

    @Override
    protected CartDiscountQueryBuilder getThis() {
        return this;
    }

    @Override
    public CartDiscountQuery build() {
        return super.build();
    }

    @Override
    public CartDiscountQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public CartDiscountQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public CartDiscountQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public CartDiscountQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public CartDiscountQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public CartDiscountQueryBuilder plusExpansionPaths(final Function<CartDiscountExpansionModel<CartDiscount>, ExpansionPathContainer<CartDiscount>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public CartDiscountQueryBuilder plusPredicates(final Function<CartDiscountQueryModel, QueryPredicate<CartDiscount>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public CartDiscountQueryBuilder plusPredicates(final QueryPredicate<CartDiscount> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public CartDiscountQueryBuilder plusPredicates(final List<QueryPredicate<CartDiscount>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public CartDiscountQueryBuilder plusSort(final Function<CartDiscountQueryModel, QuerySort<CartDiscount>> m) {
        return super.plusSort(m);
    }

    @Override
    public CartDiscountQueryBuilder plusSort(final List<QuerySort<CartDiscount>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public CartDiscountQueryBuilder plusSort(final QuerySort<CartDiscount> sort) {
        return super.plusSort(sort);
    }

    @Override
    public CartDiscountQueryBuilder predicates(final Function<CartDiscountQueryModel, QueryPredicate<CartDiscount>> m) {
        return super.predicates(m);
    }

    @Override
    public CartDiscountQueryBuilder predicates(final QueryPredicate<CartDiscount> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public CartDiscountQueryBuilder predicates(final List<QueryPredicate<CartDiscount>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public CartDiscountQueryBuilder sort(final Function<CartDiscountQueryModel, QuerySort<CartDiscount>> m) {
        return super.sort(m);
    }

    @Override
    public CartDiscountQueryBuilder sort(final List<QuerySort<CartDiscount>> sort) {
        return super.sort(sort);
    }

    @Override
    public CartDiscountQueryBuilder sort(final QuerySort<CartDiscount> sort) {
        return super.sort(sort);
    }

    @Override
    public CartDiscountQueryBuilder sortMulti(final Function<CartDiscountQueryModel, List<QuerySort<CartDiscount>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public CartDiscountQueryBuilder expansionPaths(final Function<CartDiscountExpansionModel<CartDiscount>, ExpansionPathContainer<CartDiscount>> m) {
        return super.expansionPaths(m);
    }
}
