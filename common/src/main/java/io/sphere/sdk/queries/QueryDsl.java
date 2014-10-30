package io.sphere.sdk.queries;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public interface QueryDsl<T> extends EntityQuery<T> {
    /**
     * Returns an EntityQuery with the new predicate as predicate.
     * @param predicate the new predicate
     * @return an EntityQuery with predicate
     */
    QueryDsl<T> withPredicate(final Predicate<T> predicate);

    /**
     * Returns an EntityQuery with the new sort as sort.
     * @param sort list of sorts how the results of the query should be sorted
     * @return EntityQuery with sort
     */
    QueryDsl<T> withSort(final List<Sort<T>> sort);

    QueryDsl<T> withLimit(final long limit);

    QueryDsl<T> withOffset(final long offset);

    QueryDsl<T> withExpansionPaths(final List<ExpansionPath<T>> expansionPaths);

    QueryDsl<T> withAdditionalQueryParameters(final List<QueryParameter> additionalQueryParameters);

    default QueryDsl<T> withExpansionPath(final ExpansionPath<T> expansionPath) {
        Objects.requireNonNull(expansionPath);
        return withExpansionPaths(Arrays.asList(expansionPath));
    }
}
