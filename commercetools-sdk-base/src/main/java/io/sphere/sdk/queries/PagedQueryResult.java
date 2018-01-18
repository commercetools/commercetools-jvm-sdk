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
    static <T> PagedQueryResult<T> empty() {
        return new PagedQueryResultDsl<>(0L, 0L, 0L, Collections.<T>emptyList(), 0L);
    }

    /**
     * @deprecated PagedQueryResult should remain as a read model, and be constructed only by deserialization.
     */
    @Deprecated
    static <T> PagedQueryResult<T> of(final Long offset, final Long limit, final Long total, final List<T> results) {
        return new PagedQueryResultDsl<>(offset, limit, total, results, (long) results.size());
    }

    /**
     * @deprecated PagedQueryResult should remain as a read model, and be constructed only by deserialization.
     */
    @Deprecated
    static <T> PagedQueryResult<T> of(final Long offset, final Long total, final List<T> results) {
        return of(offset, -1L, total, results);
    }

    /**
     * @deprecated PagedQueryResult should remain as a read model, and be constructed only by deserialization.
     */
    @Deprecated
    static <T> PagedQueryResult<T> of(final List<T> results) {
        final long size = results.size();
        return of(0L, size, size, results);
    }

    /**
     * @deprecated PagedQueryResult should remain as a read model, and be constructed only by deserialization.
     */
    @JsonIgnore
    static <T> PagedQueryResult<T> of(final T singleResult) {
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
