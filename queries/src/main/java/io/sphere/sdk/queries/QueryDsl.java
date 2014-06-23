package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public interface QueryDsl<I, R extends I, M> extends EntityQuery<I, M> {
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

    QueryDsl<I, R, M> withOffset(long offset);

    //TODO this is maybe optional
    TypeReference<PagedQueryResult<R>> typeReference();
}
