package io.sphere.sdk.queries;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * A container for query responses which contains a subset of the matching values.
 * @param <T> the type of the underlying model, like category or product.
 */
public class PagedQueryResult<T> extends PagedResult<T> {

    @JsonCreator
    PagedQueryResult(final Long offset, final Long total, final List<T> results, final Long count) {
        super(offset, total, results, count);
    }

    /**
     * Creates a {@code PagedQueryResult} for queries with no matching values.
     * @param <T> the type of the underlying model
     * @return an empty {@code PagedQueryResult}
     */
    public static <T> PagedQueryResultDsl<T> empty() {
        return new PagedQueryResultDsl<>(0L, 0L, Collections.<T>emptyList(), 0L);
    }

    public static <T> PagedQueryResultDsl<T> of(final Long offset, final Long total, final List<T> results) {
        return new PagedQueryResultDsl<>(offset, total, results, (long) results.size());
    }

    public static <T> PagedQueryResultDsl<T> of(final List<T> results) {
        return of(0L, (long) results.size(), results);
    }

    @JsonIgnore
    public static <T> PagedQueryResultDsl<T> of(final T singleResult) {
        return of(Collections.singletonList(singleResult));
    }

    @Override
    public Long getOffset() {
        return super.getOffset();
    }

    @Override
    public List<T> getResults() {
        return super.getResults();
    }

    /**
     * The total number of results matching the query.
     * This field is returned by default.
     * For improved performance, calculating this field can be deactivated by using {@link QueryDsl#withFetchTotal(boolean)} and {@code false}.
     *
     * {@include.example io.sphere.sdk.categories.queries.CategoryQueryIntegrationTest#withFetchTotalFalseRemovesTotalFromOutput()}
     *
     * @return total or null
     */
    @Override
    public Long getTotal() {
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
    public Long getCount() {
        return super.getCount();
    }
}
