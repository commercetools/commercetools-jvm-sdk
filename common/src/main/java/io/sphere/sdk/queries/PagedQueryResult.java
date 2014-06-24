package io.sphere.sdk.queries;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Optional;
import io.sphere.sdk.utils.ListUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A container for query responses which contains a subset of the matching values.
 * @param <T> the type of the underlying model, like category or product.
 */
public final class PagedQueryResult<T> {
    private final int offset;
    private final int count;
    private final int total;
    private final List<T> results;

    @JsonCreator
    PagedQueryResult(final int offset, final int count, final int total, final List<T> results) {
        this.offset = offset;
        this.count = count;
        this.total = total;
        this.results = results;
    }

    /**
     * The offset supplied by the client or the server default.
     * @return the amount of pages skipped
     */
    public int getOffset() {
        return offset;
    }

    /**
     * The actual number of results returned.
     * @return the number of elements in this container
     */
    public int getCount() {
        return count;
    }

    /**
     * The total number of results matching the query.
     * Total is greater or equal to count.
     * @return the number of elements that can be fetched with the used query
     */
    public int getTotal() {
        return total;
    }

    /**
     * List of results. If {@link PagedQueryResult#getCount()} is not equal
     * to {@link PagedQueryResult#getTotal()} the container contains only a subset of all
     * elements that match the query.
     * @return {@link PagedQueryResult#getCount()} elements matching the query
     */
    public List<T> getResults() {
        return results;
    }

    /**
     * Return the first element of the result list as option.
     * @return the first value or absent
     */
    public Optional<T> headOption() {
        return ListUtils.headOption(getResults());
    }

    /**
     * Creates a {@code PagedQueryResult} for queries with no matching values.
     * @param <T> the type of the underlying model
     * @return an empty {@code PagedQueryResult}
     */
    public static <T> PagedQueryResult<T> empty() {
        return new PagedQueryResult<>(0, 0, 0, Collections.<T>emptyList());
    }

    public static <T> PagedQueryResult<T> of(final int offset, final int total, final List<T> results) {
        return new PagedQueryResult<>(offset, results.size(), total, results);
    }

    public static <T> PagedQueryResult<T> of(final List<T> results) {
        return of(0, results.size(), results);
    }

    @JsonIgnore
    public static <T> PagedQueryResult<T> of(final T singleResult) {
        return of(Arrays.asList(singleResult));
    }

    @SuppressWarnings("rawtypes")//at runtime generic type is not determinable
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PagedQueryResult that = (PagedQueryResult) o;

        if (count != that.count) return false;
        if (offset != that.offset) return false;
        if (total != that.total) return false;
        if (results != null ? !results.equals(that.results) : that.results != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = offset;
        result = 31 * result + count;
        result = 31 * result + total;
        result = 31 * result + (results != null ? results.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PagedQueryResponse{" +
                "offset=" + offset +
                ", count=" + count +
                ", total=" + total +
                ", results=" + results +
                '}';
    }
}
