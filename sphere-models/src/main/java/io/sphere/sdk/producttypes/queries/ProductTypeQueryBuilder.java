package io.sphere.sdk.producttypes.queries;

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

public class ProductTypeQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<ProductTypeQueryBuilder, Category, CategoryQuery, CategoryQueryModel, CategoryExpansionModel<Category>> {

    private ProductTypeQueryBuilder(final CategoryQuery template) {
        super(template);
    }

    public static ProductTypeQueryBuilder of() {
        return new ProductTypeQueryBuilder(CategoryQuery.of());
    }

    @Override
    protected ProductTypeQueryBuilder getThis() {
        return this;
    }

    @Override
    public CategoryQuery build() {
        return super.build();
    }

    @Override
    public ProductTypeQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public ProductTypeQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public ProductTypeQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public ProductTypeQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public ProductTypeQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public ProductTypeQueryBuilder plusExpansionPaths(final Function<CategoryExpansionModel<Category>, ExpansionPathContainer<Category>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public ProductTypeQueryBuilder plusPredicates(final Function<CategoryQueryModel, QueryPredicate<Category>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public ProductTypeQueryBuilder plusPredicates(final QueryPredicate<Category> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public ProductTypeQueryBuilder plusPredicates(final List<QueryPredicate<Category>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public ProductTypeQueryBuilder plusSort(final Function<CategoryQueryModel, QuerySort<Category>> m) {
        return super.plusSort(m);
    }

    @Override
    public ProductTypeQueryBuilder plusSort(final List<QuerySort<Category>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ProductTypeQueryBuilder plusSort(final QuerySort<Category> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ProductTypeQueryBuilder predicates(final Function<CategoryQueryModel, QueryPredicate<Category>> m) {
        return super.predicates(m);
    }

    @Override
    public ProductTypeQueryBuilder predicates(final QueryPredicate<Category> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public ProductTypeQueryBuilder predicates(final List<QueryPredicate<Category>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public ProductTypeQueryBuilder sort(final Function<CategoryQueryModel, QuerySort<Category>> m) {
        return super.sort(m);
    }

    @Override
    public ProductTypeQueryBuilder sort(final List<QuerySort<Category>> sort) {
        return super.sort(sort);
    }

    @Override
    public ProductTypeQueryBuilder sort(final QuerySort<Category> sort) {
        return super.sort(sort);
    }

    @Override
    public ProductTypeQueryBuilder sortMulti(final Function<CategoryQueryModel, List<QuerySort<Category>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public ProductTypeQueryBuilder expansionPaths(final Function<CategoryExpansionModel<Category>, ExpansionPathContainer<Category>> m) {
        return super.expansionPaths(m);
    }
}
