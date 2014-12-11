package io.sphere.sdk.queries;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A container for query responses which contains a subset of the matching values.
 * @param <T> the type of the underlying model, like category or product.
 */
public final class PagedQueryResult<T> extends PagedResult<T> {

    @JsonCreator
    PagedQueryResult(final int offset, final int total, final List<T> results) {
        super(offset, total, results);
        if (size() > total) {
            throw new IllegalArgumentException(String.format("results cannot be greater than total, total=%d, offset=%d, count=%d", total, offset, size()));
        }
    }

    /**
     * Creates a copy of this item with the given offset.
     * @param offset the offset of the new copy
     * @return the copy
     */
    public PagedQueryResult<T> withOffset(final int offset) {
        return PagedQueryResult.of(offset, getTotal(), getResults());
    }

    /**
     * Creates a copy of this with the given total items count.
     * @param total the number of total items in the backend.
     * @return a copy with total as new total.
     */
    public PagedQueryResult<T> withTotal(final int total) {
        return PagedQueryResult.of(getOffset(), total, getResults());
    }

    /**
     * Creates a {@code PagedQueryResult} for queries with no matching values.
     * @param <T> the type of the underlying model
     * @return an empty {@code PagedQueryResult}
     */
    public static <T> PagedQueryResult<T> empty() {
        return new PagedQueryResult<>(0, 0, Collections.<T>emptyList());
    }

    public static <T> PagedQueryResult<T> of(final int offset, final int total, final List<T> results) {
        return new PagedQueryResult<>(offset, total, results);
    }

    public static <T> PagedQueryResult<T> of(final List<T> results) {
        return of(0, results.size(), results);
    }

    @JsonIgnore
    public static <T> PagedQueryResult<T> of(final T singleResult) {
        return of(Arrays.asList(singleResult));
    }
}
