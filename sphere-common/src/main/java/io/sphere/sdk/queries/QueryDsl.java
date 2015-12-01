package io.sphere.sdk.queries;

import io.sphere.sdk.expansion.ReferenceExpansionDsl;

import java.util.List;

/**
 *
 * @param <T> type of the query result
 * @param <C> type of the class implementing this class
 */
public interface QueryDsl<T, C extends QueryDsl<T, C>> extends EntityQuery<T>, ReferenceExpansionDsl<T, C> {

    /**
     * Returns an instance with the new predicate list as predicates.
     * @param predicates the new predicate list
     * @return instance with the predicate list
     */
    C withPredicates(final List<QueryPredicate<T>> predicates);

    C withPredicates(final QueryPredicate<T> predicate);


    /**
     * Returns an instance with the new predicate list appended to the existing predicates.
     * @param predicates the new predicate list
     * @return instance with the existing predicate list plus the new predicate list
     */
    C plusPredicates(final List<QueryPredicate<T>> predicates);

    C plusPredicates(final QueryPredicate<T> predicate);

    /**
     * Returns a query with the new sort as sort.
     * @param sort list of sorts how the results of the query should be sorted
     * @return EntityQuery with sort
     */
    C withSort(final List<QuerySort<T>> sort);

    C withSort(final QuerySort<T> sort);

    C plusSort(final List<QuerySort<T>> sort);

    C plusSort(final QuerySort<T> sort);

    C withFetchTotal(final boolean fetchTotal);

    C withLimit(final long limit);

    /**
     * Returns a new query with the new offset as offset.
     *
     * @param offset the number of items which should be omitted in the query result.
     * @return a new query
     * @throws java.lang.IllegalArgumentException if offset is
     * not between {@value io.sphere.sdk.queries.Query#MIN_OFFSET} and {@value io.sphere.sdk.queries.Query#MAX_OFFSET}.
     */
    C withOffset(final long offset);
}
