package io.sphere.sdk.queries;

import java.util.List;

public interface EntityQueryWithCopy<I, R, M> extends EntityQuery<I, R, M> {
    /**
     * Returns an EntityQuery with the new predicate as predicate.
     * @param predicate the new predicate
     * @return an EntityQuery with predicate
     */
    EntityQueryWithCopy<I, R, M> withPredicate(Predicate<M> predicate);

    /**
     * Returns an EntityQuery with the new sort as sort.
     * @param sort list of sorts how the results of the query should be sorted
     * @return EntityQuery with sort
     */
    EntityQueryWithCopy<I, R, M> withSort(List<Sort> sort);

    EntityQueryWithCopy<I, R, M> withLimit(long limit);

    EntityQueryWithCopy<I, R, M> withOffset(long limit);
}
