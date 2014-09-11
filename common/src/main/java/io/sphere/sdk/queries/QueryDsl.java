package io.sphere.sdk.queries;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public interface QueryDsl<I> extends EntityQuery<I> {
    /**
     * Returns an EntityQuery with the new predicate as predicate.
     * @param predicate the new predicate
     * @return an EntityQuery with predicate
     */
    QueryDsl<I> withPredicate(final Predicate<I> predicate);

    /**
     * Returns an EntityQuery with the new sort as sort.
     * @param sort list of sorts how the results of the query should be sorted
     * @return EntityQuery with sort
     */
    QueryDsl<I> withSort(final List<Sort<I>> sort);

    QueryDsl<I> withLimit(final long limit);

    QueryDsl<I> withOffset(final long offset);

    QueryDsl<I> withExpansionPaths(final List<ExpansionPath<I>> expansionPaths);

    default QueryDsl<I> withExpansionPaths(final ExpansionPath<I> expansionPath) {
        Objects.requireNonNull(expansionPath);
        return withExpansionPaths(Arrays.asList(expansionPath));
    }
}
