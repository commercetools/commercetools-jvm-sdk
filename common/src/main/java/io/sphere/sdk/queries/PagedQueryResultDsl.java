package io.sphere.sdk.queries;

import java.util.List;

public class PagedQueryResultDsl<T> extends PagedQueryResult<T> {
    PagedQueryResultDsl(final int offset, final int total, final List<T> results) {
        super(offset, total, results);
    }

    /**
     * Creates a copy of this item with the given offset.
     * @param offset the offset of the new copy
     * @return the copy
     */
    public PagedQueryResultDsl<T> withOffset(final int offset) {
        return PagedQueryResult.of(offset, getTotal(), getResults());
    }

    /**
     * Creates a copy of this with the given total items count.
     * @param total the number of total items in the backend.
     * @return a copy with total as new total.
     */
    public PagedQueryResultDsl<T> withTotal(final int total) {
        return PagedQueryResult.of(getOffset(), total, getResults());
    }
}
