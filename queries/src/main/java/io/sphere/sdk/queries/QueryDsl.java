package io.sphere.sdk.queries;

import java.util.Arrays;
import java.util.List;

public interface QueryDsl<I, M> extends EntityQuery<I, M> {
    /**
     * Returns an EntityQuery with the new predicate as predicate.
     * @param predicate the new predicate
     * @return an EntityQuery with predicate
     */
    QueryDsl<I, M> withPredicate(final Predicate<M> predicate);

    /**
     * Returns an EntityQuery with the new sort as sort.
     * @param sort list of sorts how the results of the query should be sorted
     * @return EntityQuery with sort
     */
    QueryDsl<I, M> withSort(final List<Sort> sort);

    QueryDsl<I, M> withLimit(final long limit);

    QueryDsl<I, M> withOffset(final long offset);

    QueryDsl<I, M> withExpansionPaths(final List<ExpansionPath<I>> expansionPaths);

    default QueryDsl<I, M> withExpansionPath(final ExpansionPath<I> expansionPath) {
        return withExpansionPaths(Arrays.asList(expansionPath));
    }
}
