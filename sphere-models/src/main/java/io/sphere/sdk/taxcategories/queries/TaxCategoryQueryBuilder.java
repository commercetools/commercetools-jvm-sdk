package io.sphere.sdk.taxcategories.queries;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

import java.util.List;
import java.util.function.Function;

/**

 {@doc.gen summary tax categories}

 */
public class TaxCategoryQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<TaxCategoryQueryBuilder, TaxCategory, TaxCategoryQuery, TaxCategoryQueryModel, TaxCategoryExpansionModel<TaxCategory>> {

    private TaxCategoryQueryBuilder(final TaxCategoryQuery template) {
        super(template);
    }

    public static TaxCategoryQueryBuilder of() {
        return new TaxCategoryQueryBuilder(TaxCategoryQuery.of());
    }

    @Override
    protected TaxCategoryQueryBuilder getThis() {
        return this;
    }

    @Override
    public TaxCategoryQuery build() {
        return super.build();
    }

    @Override
    public TaxCategoryQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public TaxCategoryQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public TaxCategoryQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public TaxCategoryQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public TaxCategoryQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public TaxCategoryQueryBuilder plusExpansionPaths(final Function<TaxCategoryExpansionModel<TaxCategory>, ExpansionPathContainer<TaxCategory>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public TaxCategoryQueryBuilder plusPredicates(final Function<TaxCategoryQueryModel, QueryPredicate<TaxCategory>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public TaxCategoryQueryBuilder plusPredicates(final QueryPredicate<TaxCategory> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public TaxCategoryQueryBuilder plusPredicates(final List<QueryPredicate<TaxCategory>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public TaxCategoryQueryBuilder plusSort(final Function<TaxCategoryQueryModel, QuerySort<TaxCategory>> m) {
        return super.plusSort(m);
    }

    @Override
    public TaxCategoryQueryBuilder plusSort(final List<QuerySort<TaxCategory>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public TaxCategoryQueryBuilder plusSort(final QuerySort<TaxCategory> sort) {
        return super.plusSort(sort);
    }

    @Override
    public TaxCategoryQueryBuilder predicates(final Function<TaxCategoryQueryModel, QueryPredicate<TaxCategory>> m) {
        return super.predicates(m);
    }

    @Override
    public TaxCategoryQueryBuilder predicates(final QueryPredicate<TaxCategory> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public TaxCategoryQueryBuilder predicates(final List<QueryPredicate<TaxCategory>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public TaxCategoryQueryBuilder sort(final Function<TaxCategoryQueryModel, QuerySort<TaxCategory>> m) {
        return super.sort(m);
    }

    @Override
    public TaxCategoryQueryBuilder sort(final List<QuerySort<TaxCategory>> sort) {
        return super.sort(sort);
    }

    @Override
    public TaxCategoryQueryBuilder sort(final QuerySort<TaxCategory> sort) {
        return super.sort(sort);
    }

    @Override
    public TaxCategoryQueryBuilder sortMulti(final Function<TaxCategoryQueryModel, List<QuerySort<TaxCategory>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public TaxCategoryQueryBuilder expansionPaths(final Function<TaxCategoryExpansionModel<TaxCategory>, ExpansionPathContainer<TaxCategory>> m) {
        return super.expansionPaths(m);
    }
}
