package io.sphere.sdk.queries;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.models.Builder;

import java.util.List;
import java.util.function.Function;

public interface ResourceMetaModelQueryDslBuilder<B, T, C extends MetaModelQueryDsl<T, C, Q, E>, Q, E> extends Builder<C> {


    B plusPredicates(final Function<Q, QueryPredicate<T>> m);


    B plusPredicates(final QueryPredicate<T> queryPredicate);


    B plusPredicates(final String queryPredicate);


    B plusPredicates(final List<QueryPredicate<T>> queryPredicates);


    B plusSort(final Function<Q, QuerySort<T>> m);


    B plusSort(final List<QuerySort<T>> sort);


    B plusSort(final QuerySort<T> sort);


    B plusSort(final String sort);


    B predicates(final Function<Q, QueryPredicate<T>> m);


    B predicates(final QueryPredicate<T> queryPredicate);


    B predicates(final String queryPredicate);


    B predicates(final List<QueryPredicate<T>> queryPredicates);


    B sort(final Function<Q, QuerySort<T>> m);


    B sort(final List<QuerySort<T>> sort);


    B sort(final QuerySort<T> sort);


    B sort(final String sort);


    B sortMulti(final Function<Q, List<QuerySort<T>>> m);


    B plusExpansionPaths(final Function<E, ExpansionPathContainer<T>> m);


    B plusExpansionPaths(final String m);


    B expansionPaths(final Function<E, ExpansionPathContainer<T>> m);


    B expansionPaths(final String m);


    B fetchTotal(final boolean fetchTotal);


    B limit(final Long limit);


    B limit(final long limit);


    B offset(final Long offset);


    B offset(final long offset);
}
