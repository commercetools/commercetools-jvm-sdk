package io.sphere.sdk.discountcodes.queries;

import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.expansion.DiscountCodeExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;

import java.util.List;
import java.util.function.Function;

/**

 {@doc.gen summary }

 */
public class DiscountCodeQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<DiscountCodeQueryBuilder, DiscountCode, DiscountCodeQuery, DiscountCodeQueryModel, DiscountCodeExpansionModel<DiscountCode>> {

    private DiscountCodeQueryBuilder(final DiscountCodeQuery template) {
        super(template);
    }

    public static DiscountCodeQueryBuilder of() {
        return new DiscountCodeQueryBuilder(DiscountCodeQuery.of());
    }

    @Override
    protected DiscountCodeQueryBuilder getThis() {
        return this;
    }

    @Override
    public DiscountCodeQuery build() {
        return super.build();
    }

    @Override
    public DiscountCodeQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public DiscountCodeQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public DiscountCodeQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public DiscountCodeQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public DiscountCodeQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public DiscountCodeQueryBuilder plusExpansionPaths(final Function<DiscountCodeExpansionModel<DiscountCode>, ExpansionPathContainer<DiscountCode>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public DiscountCodeQueryBuilder plusPredicates(final Function<DiscountCodeQueryModel, QueryPredicate<DiscountCode>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public DiscountCodeQueryBuilder plusPredicates(final QueryPredicate<DiscountCode> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public DiscountCodeQueryBuilder plusPredicates(final List<QueryPredicate<DiscountCode>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public DiscountCodeQueryBuilder plusSort(final Function<DiscountCodeQueryModel, QuerySort<DiscountCode>> m) {
        return super.plusSort(m);
    }

    @Override
    public DiscountCodeQueryBuilder plusSort(final List<QuerySort<DiscountCode>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public DiscountCodeQueryBuilder plusSort(final QuerySort<DiscountCode> sort) {
        return super.plusSort(sort);
    }

    @Override
    public DiscountCodeQueryBuilder predicates(final Function<DiscountCodeQueryModel, QueryPredicate<DiscountCode>> m) {
        return super.predicates(m);
    }

    @Override
    public DiscountCodeQueryBuilder predicates(final QueryPredicate<DiscountCode> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public DiscountCodeQueryBuilder predicates(final List<QueryPredicate<DiscountCode>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public DiscountCodeQueryBuilder sort(final Function<DiscountCodeQueryModel, QuerySort<DiscountCode>> m) {
        return super.sort(m);
    }

    @Override
    public DiscountCodeQueryBuilder sort(final List<QuerySort<DiscountCode>> sort) {
        return super.sort(sort);
    }

    @Override
    public DiscountCodeQueryBuilder sort(final QuerySort<DiscountCode> sort) {
        return super.sort(sort);
    }

    @Override
    public DiscountCodeQueryBuilder sortMulti(final Function<DiscountCodeQueryModel, List<QuerySort<DiscountCode>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public DiscountCodeQueryBuilder expansionPaths(final Function<DiscountCodeExpansionModel<DiscountCode>, ExpansionPathContainer<DiscountCode>> m) {
        return super.expansionPaths(m);
    }
}
