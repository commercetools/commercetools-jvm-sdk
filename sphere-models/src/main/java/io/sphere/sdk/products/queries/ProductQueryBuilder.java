package io.sphere.sdk.products.queries;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;

import java.util.List;
import java.util.function.Function;

/**

 {@doc.gen summary }

 */
public final class ProductQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<ProductQueryBuilder, Product, ProductQuery, ProductQueryModel, ProductExpansionModel<Product>> {

    private ProductQueryBuilder(final ProductQuery template) {
        super(template);
    }

    public static ProductQueryBuilder of() {
        return new ProductQueryBuilder(ProductQuery.of());
    }

    @Override
    protected ProductQueryBuilder getThis() {
        return this;
    }

    @Override
    public ProductQuery build() {
        return super.build();
    }

    @Override
    public ProductQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public ProductQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public ProductQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public ProductQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public ProductQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public ProductQueryBuilder plusExpansionPaths(final Function<ProductExpansionModel<Product>, ExpansionPathContainer<Product>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public ProductQueryBuilder plusPredicates(final Function<ProductQueryModel, QueryPredicate<Product>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public ProductQueryBuilder plusPredicates(final QueryPredicate<Product> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public ProductQueryBuilder plusPredicates(final List<QueryPredicate<Product>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public ProductQueryBuilder plusSort(final Function<ProductQueryModel, QuerySort<Product>> m) {
        return super.plusSort(m);
    }

    @Override
    public ProductQueryBuilder plusSort(final List<QuerySort<Product>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ProductQueryBuilder plusSort(final QuerySort<Product> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ProductQueryBuilder predicates(final Function<ProductQueryModel, QueryPredicate<Product>> m) {
        return super.predicates(m);
    }

    @Override
    public ProductQueryBuilder predicates(final QueryPredicate<Product> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public ProductQueryBuilder predicates(final List<QueryPredicate<Product>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public ProductQueryBuilder sort(final Function<ProductQueryModel, QuerySort<Product>> m) {
        return super.sort(m);
    }

    @Override
    public ProductQueryBuilder sort(final List<QuerySort<Product>> sort) {
        return super.sort(sort);
    }

    @Override
    public ProductQueryBuilder sort(final QuerySort<Product> sort) {
        return super.sort(sort);
    }

    @Override
    public ProductQueryBuilder sortMulti(final Function<ProductQueryModel, List<QuerySort<Product>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public ProductQueryBuilder expansionPaths(final Function<ProductExpansionModel<Product>, ExpansionPathContainer<Product>> m) {
        return super.expansionPaths(m);
    }
}
