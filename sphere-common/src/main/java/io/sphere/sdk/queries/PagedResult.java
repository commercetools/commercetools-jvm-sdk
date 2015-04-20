package io.sphere.sdk.queries;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.utils.ListUtils;

import java.util.List;
import java.util.Optional;

public abstract class PagedResult<T> extends Base {
    protected final int offset;
    protected final int total;
    protected final List<T> results;

    public PagedResult(final int offset, final int total, final List<T> results) {
        this.offset = offset;
        this.total = total;
        this.results = results;
    }

    /**
      * The offset supplied by the client or the server default.
      * @return the amount of items (not pages) skipped
      */
     public int getOffset() {
         return offset;
     }

    /**
      * The actual number of results returned.
      * @return the number of elements in this container
      */
     public int size() {
         return results.size();
     }

    /**
      * The total number of results matching the request.
      * Total is greater or equal to count.
      * @return the number of elements that can be fetched matching the criteria
      */
     public int getTotal() {
         return total;
     }

    /**
      * List of results. If {@link io.sphere.sdk.queries.PagedResult#size()} is not equal
      * to {@link io.sphere.sdk.queries.PagedResult#getTotal()} the container contains only a subset of all
      * elements that match the request.
      * @return {@link io.sphere.sdk.queries.PagedResult#size()} elements matching the request
      */
     public List<T> getResults() {
         return results;
     }

    /**
      * Tries to access the first element of the result list.
      * Use case: query by slug which should contain zero or one element in the result list.
      * @return the first value or absent
      */
     public Optional<T> head() {
         return ListUtils.headOption(getResults());
     }

    /**
      * Checks if this is the first page of a result.
      * @return true if offset is 0 otherwise false
      */
     public boolean isFirst() {
         return getOffset() == 0;
     }

    /**
      * Checks if it is the last possible page.
      * @return true if doing a request with an incremented offset parameter would cause an empty result otherwise false.
      */
     public boolean isLast() {
         return getOffset() + size() == getTotal();
     }
}
