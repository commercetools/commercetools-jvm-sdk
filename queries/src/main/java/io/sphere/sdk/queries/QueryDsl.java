package io.sphere.sdk.queries;

import java.util.List;

public interface QueryDsl<I, M> extends EntityQuery<I, M> {
    /**
     * Returns an EntityQuery with the new predicate as predicate.
     * @param predicate the new predicate
     * @return an EntityQuery with predicate
     */
    QueryDsl<I, M> withPredicate(Predicate<M> predicate);

    /**
     * Returns an EntityQuery with the new sort as sort.
     * @param sort list of sorts how the results of the query should be sorted
     * @return EntityQuery with sort
     */
    QueryDsl<I, M> withSort(List<Sort> sort);

    QueryDsl<I, M> withLimit(long limit);

    QueryDsl<I, M> withOffset(long offset);
}
