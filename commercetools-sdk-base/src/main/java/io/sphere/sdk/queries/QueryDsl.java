package io.sphere.sdk.queries;

import io.sphere.sdk.expansion.ReferenceExpansionDsl;

import java.util.List;

/**
 * Query request interface for immutable value classes which can copy themselves with modified fields.
 *
 * @param <T> type of the query result
 * @param <C> type of the class implementing this class
 */
public interface QueryDsl<T, C extends QueryDsl<T, C>> extends ResourceQuery<T>, ReferenceExpansionDsl<T, C> {

    /**
     * Returns a new instance with the new predicate list as predicates.
     * @param predicates the new predicate list
     * @return instance with the predicate list
     * @see #plusPredicates(List)
     */
    C withPredicates(final List<QueryPredicate<T>> predicates);

    /**
     * Returns a new instance with the new predicate as only predicate.
     * @param predicate the new predicate
     * @return instance with the predicate
     * @see #plusPredicates(QueryPredicate)
     */
    C withPredicates(final QueryPredicate<T> predicate);

    /**
     * Returns a new instance with the new predicate as only predicate.
     * @param predicate the new predicate
     * @return instance with the predicate
     * @see #plusPredicates(QueryPredicate)
     */
    C withPredicates(final String predicate);

    /**
     * Returns a new instance with the new predicate list appended to the existing predicates (AND semantic).
     * @param predicates the new predicate list
     * @return instance with the existing predicate list plus the new predicate list
     * @see #withPredicates(List)
     */
    C plusPredicates(final List<QueryPredicate<T>> predicates);

    /**
     * Returns a new instance with the new predicate appended to the existing predicates (AND semantic).
     * @param predicate the new predicate
     * @return instance with the existing predicate list plus the new predicate list
     * @see #withPredicates(QueryPredicate)
     */
    C plusPredicates(final QueryPredicate<T> predicate);

    /**
     * Returns a new instance with the new predicate appended to the existing predicates (AND semantic).
     * @param predicate the new predicate
     * @return instance with the existing predicate list plus the new predicate list
     * @see #withPredicates(QueryPredicate)
     */
    C plusPredicates(final String predicate);

    /**
     * Returns a query with the new sort list as only sort parameters.
     * @param sort list of sorts how the results of the query should be sorted
     * @return Query with sort
     * @see #plusSort(List)
     */
    C withSort(final List<QuerySort<T>> sort);

    /**
     * Returns a query with the new sort as only sort parameter.
     * @param sort sort expression how the results of the query should be sorted
     * @return Query with sort
     * @see #plusSort(QuerySort)
     */
    C withSort(final QuerySort<T> sort);

    /**
     * Returns a query with the new sort as only sort parameter.
     * @param sort sort expression how the results of the query should be sorted
     * @return Query with sort
     * @see #plusSort(QuerySort)
     */
    C withSort(final String sort);

    /**
     * Returns a query with the sort expressions appended to the existing ones.
     * @param sort sort expressions how the results of the query should be sorted
     * @return Query with sort
     * @see #withSort(List)
     */
    C plusSort(final List<QuerySort<T>> sort);

    /**
     * Returns a query with the sort expression appended to the existing ones.
     * @param sort sort expression how the results of the query should be sorted
     * @return Query with sort
     * @see #withSort(QuerySort)
     */
    C plusSort(final QuerySort<T> sort);

    /**
     * Returns a query with the sort expression appended to the existing ones.
     * @param sort sort expression how the results of the query should be sorted
     * @return Query with sort
     * @see #withSort(QuerySort)
     */
    C plusSort(final String sort);

    /**
     * Enables/disables a flag it the total amount of items should be counted.
     * <p>If {@code fetchTotal} is true then {@link PagedQueryResult#getTotal()} is null.</p>
     * @param fetchTotal enable the total count if true (default on the construction of the QueryDsl).
     * @return the same {@link QueryDsl} as this instance but with the updated flag
     */
    C withFetchTotal(final boolean fetchTotal);

    /**
     * Creates a new instance which a limited amount of results.
     * <p><a href="https://docs.commercetools.com/http-api.html#limit">Limit restrictions on the API.</a></p>
     * @param limit the maximum amount of items of {@code T} which should be included in the {@link PagedQueryResult}.
     * @return copy of this item with modified limit settings
     * @see PagedQueryResult#getResults()
     */
    C withLimit(final Long limit);

    /**
     * Creates a new instance which a limited amount of results.
     * <p><a href="https://docs.commercetools.com/http-api.html#limit">Limit restrictions on the API.</a></p>
     * @param limit the maximum amount of items of {@code T} which should be included in the {@link PagedQueryResult}.
     * @return copy of this item with modified limit settings
     * @see PagedQueryResult#getResults()
     */
    default C withLimit(final long limit) {
        return withLimit(Long.valueOf(limit));
    }

    /**
     * Returns a new query with the new offset as offset.
     *
     * @param offset the number of items which should be omitted in the query result.
     * @return a new query
     * @throws java.lang.IllegalArgumentException if offset is
     * not between {@value io.sphere.sdk.queries.Query#MIN_OFFSET} and {@value io.sphere.sdk.queries.Query#MAX_OFFSET}.
     */
    C withOffset(final Long offset);

    /**
     * Returns a new query with the new offset as offset.
     *
     * @param offset the number of items which should be omitted in the query result.
     * @return a new query
     * @throws java.lang.IllegalArgumentException if offset is
     * not between {@value io.sphere.sdk.queries.Query#MIN_OFFSET} and {@value io.sphere.sdk.queries.Query#MAX_OFFSET}.
     */
    default C withOffset(final long offset) {
        return withOffset(Long.valueOf(offset));
    }
}
