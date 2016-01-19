package io.sphere.sdk.reviews.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.categories.queries.CategoryQueryModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;

import java.util.List;
import java.util.function.Function;

public class ReviewQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<ReviewQueryBuilder, Category, CategoryQuery, CategoryQueryModel, CategoryExpansionModel<Category>> {

    private ReviewQueryBuilder(final CategoryQuery template) {
        super(template);
    }

    public static ReviewQueryBuilder of() {
        return new ReviewQueryBuilder(CategoryQuery.of());
    }

    @Override
    protected ReviewQueryBuilder getThis() {
        return this;
    }

    @Override
    public CategoryQuery build() {
        return super.build();
    }

    @Override
    public ReviewQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public ReviewQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public ReviewQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public ReviewQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public ReviewQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public ReviewQueryBuilder plusExpansionPaths(final Function<CategoryExpansionModel<Category>, ExpansionPathContainer<Category>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public ReviewQueryBuilder plusPredicates(final Function<CategoryQueryModel, QueryPredicate<Category>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public ReviewQueryBuilder plusPredicates(final QueryPredicate<Category> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public ReviewQueryBuilder plusPredicates(final List<QueryPredicate<Category>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public ReviewQueryBuilder plusSort(final Function<CategoryQueryModel, QuerySort<Category>> m) {
        return super.plusSort(m);
    }

    @Override
    public ReviewQueryBuilder plusSort(final List<QuerySort<Category>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ReviewQueryBuilder plusSort(final QuerySort<Category> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ReviewQueryBuilder predicates(final Function<CategoryQueryModel, QueryPredicate<Category>> m) {
        return super.predicates(m);
    }

    @Override
    public ReviewQueryBuilder predicates(final QueryPredicate<Category> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public ReviewQueryBuilder predicates(final List<QueryPredicate<Category>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public ReviewQueryBuilder sort(final Function<CategoryQueryModel, QuerySort<Category>> m) {
        return super.sort(m);
    }

    @Override
    public ReviewQueryBuilder sort(final List<QuerySort<Category>> sort) {
        return super.sort(sort);
    }

    @Override
    public ReviewQueryBuilder sort(final QuerySort<Category> sort) {
        return super.sort(sort);
    }

    @Override
    public ReviewQueryBuilder sortMulti(final Function<CategoryQueryModel, List<QuerySort<Category>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public ReviewQueryBuilder expansionPaths(final Function<CategoryExpansionModel<Category>, ExpansionPathContainer<Category>> m) {
        return super.expansionPaths(m);
    }
}
