package io.sphere.sdk.queries;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Collections;
import java.util.List;

/**
 * A container for query responses which contains a subset of the matching values.
 *
 * @param <T> the type of the underlying model, like category or product.
 */
@JsonDeserialize(as = PagedQueryResultImpl.class)
public interface PagedQueryResult<T> extends PagedResult<T> {

    /**
     * Creates a {@code PagedQueryResult} for queries with no matching values.
     *
     * @param <T> the type of the underlying model
     * @return an empty {@code PagedQueryResult}
     */
    static <T> PagedQueryResultDsl<T> empty() {
        return new PagedQueryResultDsl<>(0L, 0L, 0L, Collections.<T>emptyList(), 0L);
    }

    static <T> PagedQueryResultDsl<T> of(final Long offset, final Long limit, final Long total, final List<T> results) {
        return new PagedQueryResultDsl<>(offset, limit, total, results, (long) results.size());
    }

    static <T> PagedQueryResultDsl<T> of(final List<T> results) {
        final long size = results.size();
        return of(0L, size, size, results);
    }

    @JsonIgnore
    static <T> PagedQueryResultDsl<T> of(final T singleResult) {
        return of(Collections.singletonList(singleResult));
    }

    @Override
    Long getCount();

    @Override
    Long getOffset();

    @Override
    Long getLimit();

    @Override
    List<T> getResults();

    @Override
    Long getTotal();
}
