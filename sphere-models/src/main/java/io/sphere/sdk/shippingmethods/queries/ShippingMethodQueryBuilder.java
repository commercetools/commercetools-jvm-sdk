package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

import java.util.List;
import java.util.function.Function;

/**

 {@doc.gen summary }

 */
public class ShippingMethodQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<ShippingMethodQueryBuilder, ShippingMethod, ShippingMethodQuery, ShippingMethodQueryModel, ShippingMethodExpansionModel<ShippingMethod>> {

    private ShippingMethodQueryBuilder(final ShippingMethodQuery template) {
        super(template);
    }

    public static ShippingMethodQueryBuilder of() {
        return new ShippingMethodQueryBuilder(ShippingMethodQuery.of());
    }

    @Override
    protected ShippingMethodQueryBuilder getThis() {
        return this;
    }

    @Override
    public ShippingMethodQuery build() {
        return super.build();
    }

    @Override
    public ShippingMethodQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public ShippingMethodQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public ShippingMethodQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public ShippingMethodQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public ShippingMethodQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public ShippingMethodQueryBuilder plusExpansionPaths(final Function<ShippingMethodExpansionModel<ShippingMethod>, ExpansionPathContainer<ShippingMethod>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public ShippingMethodQueryBuilder plusPredicates(final Function<ShippingMethodQueryModel, QueryPredicate<ShippingMethod>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public ShippingMethodQueryBuilder plusPredicates(final QueryPredicate<ShippingMethod> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public ShippingMethodQueryBuilder plusPredicates(final List<QueryPredicate<ShippingMethod>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public ShippingMethodQueryBuilder plusSort(final Function<ShippingMethodQueryModel, QuerySort<ShippingMethod>> m) {
        return super.plusSort(m);
    }

    @Override
    public ShippingMethodQueryBuilder plusSort(final List<QuerySort<ShippingMethod>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ShippingMethodQueryBuilder plusSort(final QuerySort<ShippingMethod> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ShippingMethodQueryBuilder predicates(final Function<ShippingMethodQueryModel, QueryPredicate<ShippingMethod>> m) {
        return super.predicates(m);
    }

    @Override
    public ShippingMethodQueryBuilder predicates(final QueryPredicate<ShippingMethod> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public ShippingMethodQueryBuilder predicates(final List<QueryPredicate<ShippingMethod>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public ShippingMethodQueryBuilder sort(final Function<ShippingMethodQueryModel, QuerySort<ShippingMethod>> m) {
        return super.sort(m);
    }

    @Override
    public ShippingMethodQueryBuilder sort(final List<QuerySort<ShippingMethod>> sort) {
        return super.sort(sort);
    }

    @Override
    public ShippingMethodQueryBuilder sort(final QuerySort<ShippingMethod> sort) {
        return super.sort(sort);
    }

    @Override
    public ShippingMethodQueryBuilder sortMulti(final Function<ShippingMethodQueryModel, List<QuerySort<ShippingMethod>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public ShippingMethodQueryBuilder expansionPaths(final Function<ShippingMethodExpansionModel<ShippingMethod>, ExpansionPathContainer<ShippingMethod>> m) {
        return super.expansionPaths(m);
    }
}
