package io.sphere.sdk.queries;

import io.sphere.sdk.expansion.ReferenceExpandeableDsl;

import java.util.Collections;
import java.util.List;

/**
 *
 * @param <T> type of the query result
 * @param <C> type of the class implementing this class
 */
public interface QueryDsl<T, C extends QueryDsl<T, C>> extends EntityQuery<T>, ReferenceExpandeableDsl<T, C> {
    /**
     * Returns an EntityQuery with the new predicate as predicate.
     * @param predicate the new predicate
     * @return an EntityQuery with predicate
     */
    C withPredicates(final QueryPredicate<T> predicate);

    C plusPredicates(final QueryPredicate<T> predicate);

    C withPredicates(final List<QueryPredicate<T>> predicates);

    /**
     * Returns a query with the new sort as sort.
     * @param sort list of sorts how the results of the query should be sorted
     * @return EntityQuery with sort
     */
    C withSort(final List<QuerySort<T>> sort);

    default C withSort(final QuerySort<T> sort) {
        return withSort(Collections.singletonList(sort));
    }

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
