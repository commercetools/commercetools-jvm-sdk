package io.sphere.sdk.queries;

import java.util.List;
import java.util.Objects;

import static io.sphere.sdk.utils.ListUtils.listOf;
import static java.util.Arrays.asList;

public interface QueryDsl<T> extends EntityQuery<T> {
    /**
     * Returns an EntityQuery with the new predicate as predicate.
     * @param predicate the new predicate
     * @return an EntityQuery with predicate
     */
    QueryDsl<T> withPredicate(final Predicate<T> predicate);

    /**
     * Returns a query with the new sort as sort.
     * @param sort list of sorts how the results of the query should be sorted
     * @return EntityQuery with sort
     */
    QueryDsl<T> withSort(final List<Sort<T>> sort);

    default QueryDsl<T> withSort(final Sort<T> sort) {
        return withSort(asList(sort));
    }

    QueryDsl<T> withLimit(final long limit);

    /**
     * Returns a new query with the new offset as offset.
     *
     * @param offset the number of items which should be omitted in the query result.
     * @return a new query
     * @throws InvalidQueryOffsetException if offset is
     * not between {@value io.sphere.sdk.queries.Query#MIN_OFFSET} and {@value io.sphere.sdk.queries.Query#MAX_OFFSET}.
     */
    QueryDsl<T> withOffset(final long offset);

    QueryDsl<T> withExpansionPaths(final List<ExpansionPath<T>> expansionPaths);

    default QueryDsl<T> plusExpansionPath(final ExpansionPath<T> expansionPath) {
        return withExpansionPaths(listOf(expansionPaths(), expansionPath));
    }

    QueryDsl<T> withAdditionalQueryParameters(final List<QueryParameter> additionalQueryParameters);

    default QueryDsl<T> withExpansionPath(final ExpansionPath<T> expansionPath) {
        Objects.requireNonNull(expansionPath);
        return withExpansionPaths(asList(expansionPath));
    }
}
