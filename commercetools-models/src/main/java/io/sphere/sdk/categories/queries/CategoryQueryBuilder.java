package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;

import java.util.List;
import java.util.function.Function;

/**

 {@doc.gen summary }

 */
public final class CategoryQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<CategoryQueryBuilder, Category, CategoryQuery, CategoryQueryModel, CategoryExpansionModel<Category>> {

    private CategoryQueryBuilder(final CategoryQuery template) {
        super(template);
    }

    public static CategoryQueryBuilder of() {
        return new CategoryQueryBuilder(CategoryQuery.of());
    }

    @Override
    protected CategoryQueryBuilder getThis() {
        return this;
    }

    @Override
    public CategoryQuery build() {
        return super.build();
    }

    @Override
    public CategoryQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public CategoryQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public CategoryQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public CategoryQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public CategoryQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public CategoryQueryBuilder plusExpansionPaths(final Function<CategoryExpansionModel<Category>, ExpansionPathContainer<Category>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public CategoryQueryBuilder plusPredicates(final Function<CategoryQueryModel, QueryPredicate<Category>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public CategoryQueryBuilder plusPredicates(final QueryPredicate<Category> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public CategoryQueryBuilder plusPredicates(final List<QueryPredicate<Category>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public CategoryQueryBuilder plusSort(final Function<CategoryQueryModel, QuerySort<Category>> m) {
        return super.plusSort(m);
    }

    @Override
    public CategoryQueryBuilder plusSort(final List<QuerySort<Category>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public CategoryQueryBuilder plusSort(final QuerySort<Category> sort) {
        return super.plusSort(sort);
    }

    @Override
    public CategoryQueryBuilder predicates(final Function<CategoryQueryModel, QueryPredicate<Category>> m) {
        return super.predicates(m);
    }

    @Override
    public CategoryQueryBuilder predicates(final QueryPredicate<Category> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public CategoryQueryBuilder predicates(final List<QueryPredicate<Category>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public CategoryQueryBuilder sort(final Function<CategoryQueryModel, QuerySort<Category>> m) {
        return super.sort(m);
    }

    @Override
    public CategoryQueryBuilder sort(final List<QuerySort<Category>> sort) {
        return super.sort(sort);
    }

    @Override
    public CategoryQueryBuilder sort(final QuerySort<Category> sort) {
        return super.sort(sort);
    }

    @Override
    public CategoryQueryBuilder sortMulti(final Function<CategoryQueryModel, List<QuerySort<Category>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public CategoryQueryBuilder expansionPaths(final Function<CategoryExpansionModel<Category>, ExpansionPathContainer<Category>> m) {
        return super.expansionPaths(m);
    }
}
