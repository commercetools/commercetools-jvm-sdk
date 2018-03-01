package io.sphere.sdk.queries;

import io.sphere.sdk.models.Base;

import java.util.List;

public abstract class PagedResultBase<T> extends Base implements PagedResult<T> {
    protected final Long offset;
    protected final Long limit;
    protected final Long total;
    protected final List<T> results;
    protected final Long count;

    protected PagedResultBase(final Long offset, final Long limit, final Long total, final List<T> results, final Long count) {
        this.offset = offset;
        this.limit = limit;
        this.total = total;
        this.results = results;
        this.count = count;
    }

    /**
     * The offset supplied by the client or the server default.
     * @return the amount of items (not pages) skipped
     */
    public Long getOffset() {
        return offset;
    }

    /**
     * The limit supplied by the client or the server default.
     * @return the maximum amount of items allowed to be included in the results
     */
    @Override
    public Long getLimit() {
        return limit;
    }

    /**
     * The actual number of results returned.
     * @return the number of elements in this container
     * @deprecated use {@link #getCount()} instead
     */
    @Deprecated//don't remove soon!!!
    public Long size() {
        return (long) results.size();
    }

    /**
     * The actual number of results returned.
     * @return the number of elements in this container
     */
    public Long getCount() {
        return count;
    }

    /**
     * The total number of results matching the request.
     * Total is greater or equal to count.
     * @return the number of elements that can be fetched matching the criteria
     */
    public Long getTotal() {
        return total;
    }

    /**
     * List of results. If {@link PagedResultBase#getCount()} is not equal
     * to {@link PagedResultBase#getTotal()} the container contains only a subset of all
     * elements that match the request.
     * @return {@link PagedResultBase#getCount()} elements matching the request
     */
    public List<T> getResults() {
        return results;
    }
}
