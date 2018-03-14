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

    /**
     * @deprecated PagedQueryResult should remain as a read model, and be constructed only by deserialization.
     * @param <T> the type of the underlying model
     * @param limit The limit supplied by the client or the server default
     * @param offset The offset supplied by the client or the server default.
     * @param total  The total number of results matching the request.
     * @param results the mocked results.
     * @return the paged query result.
     */
    @Deprecated
    static <T> PagedQueryResultDsl<T> of(final Long offset, final Long limit, final Long total, final List<T> results) {
        return new PagedQueryResultDsl<>(offset, limit, total, results, (long) results.size());
    }

    /**
     * @param <T> the type of the underlying model
     * @param offset The offset supplied by the client or the server default.
     * @param total  The total number of results matching the request.
     * @param results The mocked result.
     * @return the paged query result.
     * @deprecated PagedQueryResult should remain as a read model, and be constructed only by deserialization.
     */
    @Deprecated
    static <T> PagedQueryResultDsl<T> of(final Long offset, final Long total, final List<T> results) {
        return of(offset, -1L, total, results);
    }

    /**
     * @param <T> the type of the underlying model
     * @param results The mocked result.
     * @return the paged query result.
     * @deprecated PagedQueryResult should remain as a read model, and be constructed only by deserialization.
     */
    @Deprecated
    static <T> PagedQueryResultDsl<T> of(final List<T> results) {
        final long size = results.size();
        return of(0L, size, size, results);
    }

    /**
     * @param <T> the type of the underlying model
     * @param singleResult the single result expected in the return.
     * @deprecated PagedQueryResult should remain as a read model, and be constructed only by deserialization.
     * @return the paged query result.
     */
    @JsonIgnore
    static <T> PagedQueryResultDsl<T> of(final T singleResult) {
        return of(Collections.singletonList(singleResult));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Long getCount();

    /**
     * {@inheritDoc}
     */
    @Override
    Long getOffset();

    /**
     * {@inheritDoc}
     */
    @Override
    Long getLimit();

    /**
     * {@inheritDoc}
     */
    @Override
    List<T> getResults();

    /**
     * {@inheritDoc}
     */
    @Override
    Long getTotal();
}
