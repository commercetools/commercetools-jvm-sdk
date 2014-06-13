package io.sphere.sdk.queries;

import java.util.List;

public interface QueryDsl<I, R, M> extends EntityQuery<I, R, M> {
    /**
     * Returns an EntityQuery with the new predicate as predicate.
     * @param predicate the new predicate
     * @return an EntityQuery with predicate
     */
    QueryDsl<I, R, M> withPredicate(Predicate<M> predicate);

    /**
     * Returns an EntityQuery with the new sort as sort.
     * @param sort list of sorts how the results of the query should be sorted
     * @return EntityQuery with sort
     */
    QueryDsl<I, R, M> withSort(List<Sort> sort);

    QueryDsl<I, R, M> withLimit(long limit);

    QueryDsl<I, R, M> withOffset(long limit);
}
