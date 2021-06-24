package io.sphere.sdk.queries;

import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.http.NameValuePair;

import java.util.List;
import java.util.function.Function;

/**
 * Internal query request interface for immutable value classes which can copy themselves with modified fields providing a meta model for convenient creation of expressions.
 *
 * @param <T> type of the query result
 * @param <C> type of the class implementing this class
 * @param <Q> type of the query model
 * @param <E> type of the expansion model
 */
public interface MetaModelQueryDsl<T, C extends MetaModelQueryDsl<T, C, Q, E>, Q, E> extends ResourceQuery<T>, QueryDsl<T, C>, MetaModelReferenceExpansionDsl<T, C, E> {

    @Override
    C withPredicates(final List<QueryPredicate<T>> queryPredicates);

    @Override
    C withPredicates(final QueryPredicate<T> queryPredicate);

    @Override
    C withPredicates(final String queryPredicate);

    /**
     * Returns a new instance with the new predicate as only predicate.
     * @param predicateFunction function given a meta model description of the model return a {@link QueryPredicate}
     * @return instance with the predicate
     * @see #plusPredicates(Function)
     */
    C withPredicates(final Function<Q, QueryPredicate<T>> predicateFunction);

    @Override
    C plusPredicates(final List<QueryPredicate<T>> queryPredicates);

    @Override
    C plusPredicates(final QueryPredicate<T> queryPredicate);

    @Override
    C plusPredicates(final String queryPredicate);

    /**
     * Returns a new instance with the new predicate appended (AND semantics).
     * @param predicateFunction function given a meta model description of the model return a {@link QueryPredicate}
     * @return instance with the predicate appended
     * @see #withPredicates(Function)
     */
    C plusPredicates(final Function<Q, QueryPredicate<T>> predicateFunction);

    @Override
    C withSort(final List<QuerySort<T>> sort);

    @Override
    C withSort(final QuerySort<T> sort);

    @Override
    C withSort(final String sort);

    /**
     * Returns a query with the new sort as only sort parameter.
     * @param sortFunction function given a meta model description of the model returns a {@link QuerySort}
     * @return Query with sort
     * @see #plusSort(Function)
     */
    C withSort(final Function<Q, QuerySort<T>> sortFunction);

    /**
     * Returns a query with the new sort list as only sort parameters.
     * @param sortFunction function given a meta model description of the model returns a list of {@link QuerySort}
     * @return Query with sort
     * @see #plusSort(List)
     * @see #withSort(Function)
     */
    C withSortMulti(final Function<Q, List<QuerySort<T>>> sortFunction);

    @Override
    C plusSort(final List<QuerySort<T>> sort);

    @Override
    C plusSort(final QuerySort<T> sort);

    @Override
    C plusSort(final String sort);

    /**
     * Returns a query with the sort expression appended to the existing ones.
     * @param sortFunction function given a meta model description of the model returns a list of {@link QuerySort}
     * @return Query with sort
     * @see #withSort(Function)
     */
    C plusSort(final Function<Q, QuerySort<T>> sortFunction);

    C withQueryParam(NameValuePair param);
}
