package io.sphere.sdk.queries;

import java.util.List;

public final class PagedQueryResultDsl<T> extends PagedQueryResultImpl<T> {
    PagedQueryResultDsl(final Long offset, final Long limit, final Long total, final List<T> results, final Long count) {
        super(offset, limit, total, results, count);
    }

    /**
     * Creates a copy of this item with the given offset.
     * @param offset the offset of the new copy
     * @return the copy
     */
    public PagedQueryResultDsl<T> withOffset(final Long offset) {
        return PagedQueryResult.of(offset, getLimit(), getTotal(), getResults());
    }

    /**
     * Creates a copy of this with the given total items count.
     * @param total the number of total items in the backend.
     * @return a copy with total as new total.
     */
    public PagedQueryResultDsl<T> withTotal(final Long total) {
        return PagedQueryResult.of(getOffset(), getLimit(), total, getResults());
    }
}
