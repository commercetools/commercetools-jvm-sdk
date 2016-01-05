package io.sphere.sdk.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;

import java.util.List;
import java.util.function.Function;

/**
 *
 * @param <T> type of the query result
 * @param <C> type of the class implementing this class
 * @param <Q> type of the query model
 * @param <E> type of the expansion model
 */
public interface MetaModelQueryDsl<T, C extends MetaModelQueryDsl<T, C, Q, E>, Q, E> extends ResourceQuery<T>, QueryDsl<T, C>, MetaModelExpansionDsl<T, C, E> {

    @Override
    C withPredicates(final List<QueryPredicate<T>> queryPredicates);

    @Override
    C withPredicates(final QueryPredicate<T> queryPredicate);

    C withPredicates(final Function<Q, QueryPredicate<T>> m);

    @Override
    C plusPredicates(final List<QueryPredicate<T>> queryPredicates);

    @Override
    C plusPredicates(final QueryPredicate<T> queryPredicate);

    C plusPredicates(final Function<Q, QueryPredicate<T>> m);

    @Override
    C withSort(final List<QuerySort<T>> sort);

    @Override
    C withSort(final QuerySort<T> sort);

    C withSort(final Function<Q, QuerySort<T>> m);

    C withSortMulti(final Function<Q, List<QuerySort<T>>> m);

    @Override
    C plusSort(final List<QuerySort<T>> sort);

    @Override
    C plusSort(final QuerySort<T> sort);

    C plusSort(final Function<Q, QuerySort<T>> m);
}
