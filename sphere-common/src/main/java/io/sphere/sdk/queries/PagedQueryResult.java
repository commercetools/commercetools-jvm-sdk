package io.sphere.sdk.queries;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * A container for query responses which contains a subset of the matching values.
 * @param <T> the type of the underlying model, like category or product.
 */
public class PagedQueryResult<T> extends PagedResult<T> {

    @JsonCreator
    PagedQueryResult(final int offset, final int total, final List<T> results) {
        super(offset, total, results);
        if (size() > total) {
            throw new IllegalArgumentException(String.format("results cannot be greater than total, total=%d, offset=%d, count=%d", total, offset, size()));
        }
    }

    /**
     * Creates a {@code PagedQueryResult} for queries with no matching values.
     * @param <T> the type of the underlying model
     * @return an empty {@code PagedQueryResult}
     */
    public static <T> PagedQueryResultDsl<T> empty() {
        return new PagedQueryResultDsl<>(0, 0, Collections.<T>emptyList());
    }

    public static <T> PagedQueryResultDsl<T> of(final int offset, final int total, final List<T> results) {
        return new PagedQueryResultDsl<>(offset, total, results);
    }

    public static <T> PagedQueryResultDsl<T> of(final List<T> results) {
        return of(0, results.size(), results);
    }

    @JsonIgnore
    public static <T> PagedQueryResultDsl<T> of(final T singleResult) {
        return of(Arrays.asList(singleResult));
    }

    @Override
    public Integer getOffset() {
        return super.getOffset();
    }

    @Override
    public List<T> getResults() {
        return super.getResults();
    }

    @Override
    public Integer getTotal() {
        return super.getTotal();
    }

    @Override
    public Optional<T> head() {
        return super.head();
    }

    @Override
    public boolean isFirst() {
        return super.isFirst();
    }

    @Override
    public boolean isLast() {
        return super.isLast();
    }

    @Override
    public int size() {
        return super.size();
    }
}
