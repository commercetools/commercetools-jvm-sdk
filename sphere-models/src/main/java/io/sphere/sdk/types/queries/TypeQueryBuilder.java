package io.sphere.sdk.types.queries;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.types.expansion.TypeExpansionModel;

import java.util.List;
import java.util.function.Function;

public class TypeQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<TypeQueryBuilder, Type, TypeQuery, TypeQueryModel, TypeExpansionModel<Type>> {

    private TypeQueryBuilder(final TypeQuery template) {
        super(template);
    }

    public static TypeQueryBuilder of() {
        return new TypeQueryBuilder(TypeQuery.of());
    }

    @Override
    protected TypeQueryBuilder getThis() {
        return this;
    }

    @Override
    public TypeQuery build() {
        return super.build();
    }

    @Override
    public TypeQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public TypeQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public TypeQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public TypeQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public TypeQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public TypeQueryBuilder plusExpansionPaths(final Function<TypeExpansionModel<Type>, ExpansionPathContainer<Type>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public TypeQueryBuilder plusPredicates(final Function<TypeQueryModel, QueryPredicate<Type>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public TypeQueryBuilder plusPredicates(final QueryPredicate<Type> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public TypeQueryBuilder plusPredicates(final List<QueryPredicate<Type>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public TypeQueryBuilder plusSort(final Function<TypeQueryModel, QuerySort<Type>> m) {
        return super.plusSort(m);
    }

    @Override
    public TypeQueryBuilder plusSort(final List<QuerySort<Type>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public TypeQueryBuilder plusSort(final QuerySort<Type> sort) {
        return super.plusSort(sort);
    }

    @Override
    public TypeQueryBuilder predicates(final Function<TypeQueryModel, QueryPredicate<Type>> m) {
        return super.predicates(m);
    }

    @Override
    public TypeQueryBuilder predicates(final QueryPredicate<Type> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public TypeQueryBuilder predicates(final List<QueryPredicate<Type>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public TypeQueryBuilder sort(final Function<TypeQueryModel, QuerySort<Type>> m) {
        return super.sort(m);
    }

    @Override
    public TypeQueryBuilder sort(final List<QuerySort<Type>> sort) {
        return super.sort(sort);
    }

    @Override
    public TypeQueryBuilder sort(final QuerySort<Type> sort) {
        return super.sort(sort);
    }

    @Override
    public TypeQueryBuilder sortMulti(final Function<TypeQueryModel, List<QuerySort<Type>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public TypeQueryBuilder expansionPaths(final Function<TypeExpansionModel<Type>, ExpansionPathContainer<Type>> m) {
        return super.expansionPaths(m);
    }
}
