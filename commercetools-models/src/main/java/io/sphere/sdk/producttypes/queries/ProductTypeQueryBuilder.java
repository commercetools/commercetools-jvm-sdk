package io.sphere.sdk.producttypes.queries;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;

import java.util.List;
import java.util.function.Function;

/**

 {@doc.gen summary }

 */
public final class ProductTypeQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<ProductTypeQueryBuilder, ProductType, ProductTypeQuery, ProductTypeQueryModel, ProductTypeExpansionModel<ProductType>> {

    private ProductTypeQueryBuilder(final ProductTypeQuery template) {
        super(template);
    }

    public static ProductTypeQueryBuilder of() {
        return new ProductTypeQueryBuilder(ProductTypeQuery.of());
    }

    @Override
    protected ProductTypeQueryBuilder getThis() {
        return this;
    }

    @Override
    public ProductTypeQuery build() {
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
    public ProductTypeQueryBuilder plusExpansionPaths(final Function<ProductTypeExpansionModel<ProductType>, ExpansionPathContainer<ProductType>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public ProductTypeQueryBuilder plusPredicates(final Function<ProductTypeQueryModel, QueryPredicate<ProductType>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public ProductTypeQueryBuilder plusPredicates(final QueryPredicate<ProductType> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public ProductTypeQueryBuilder plusPredicates(final List<QueryPredicate<ProductType>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public ProductTypeQueryBuilder plusSort(final Function<ProductTypeQueryModel, QuerySort<ProductType>> m) {
        return super.plusSort(m);
    }

    @Override
    public ProductTypeQueryBuilder plusSort(final List<QuerySort<ProductType>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ProductTypeQueryBuilder plusSort(final QuerySort<ProductType> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ProductTypeQueryBuilder predicates(final Function<ProductTypeQueryModel, QueryPredicate<ProductType>> m) {
        return super.predicates(m);
    }

    @Override
    public ProductTypeQueryBuilder predicates(final QueryPredicate<ProductType> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public ProductTypeQueryBuilder predicates(final List<QueryPredicate<ProductType>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public ProductTypeQueryBuilder sort(final Function<ProductTypeQueryModel, QuerySort<ProductType>> m) {
        return super.sort(m);
    }

    @Override
    public ProductTypeQueryBuilder sort(final List<QuerySort<ProductType>> sort) {
        return super.sort(sort);
    }

    @Override
    public ProductTypeQueryBuilder sort(final QuerySort<ProductType> sort) {
        return super.sort(sort);
    }

    @Override
    public ProductTypeQueryBuilder sortMulti(final Function<ProductTypeQueryModel, List<QuerySort<ProductType>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public ProductTypeQueryBuilder expansionPaths(final Function<ProductTypeExpansionModel<ProductType>, ExpansionPathContainer<ProductType>> m) {
        return super.expansionPaths(m);
    }
}
