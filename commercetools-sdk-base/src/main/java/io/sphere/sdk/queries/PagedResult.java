package io.sphere.sdk.queries;

import java.util.List;
import java.util.Optional;

public interface PagedResult<T> {
     /**
      * The offset supplied by the client or the server default.
      * @return the amount of items (not pages) skipped
      */
     Long getOffset();

    /**
     * The actual number of results returned.
     * @return the number of elements in this container
     * @deprecated use {@link #getCount()} instead
     */
    @Deprecated//don't remove soon!!!
    Long size();

    /**
     * The actual number of results returned.
     * @return the number of elements in this container
     */
    Long getCount();

    /**
      * The total number of results matching the request.
      * Total is greater or equal to count.
      * @return the number of elements that can be fetched matching the criteria
      */
    Long getTotal();

    /**
      * List of results. If {@link io.sphere.sdk.queries.PagedResult#getCount()} is not equal
      * to {@link io.sphere.sdk.queries.PagedResult#getTotal()} the container contains only a subset of all
      * elements that match the request.
      * @return {@link io.sphere.sdk.queries.PagedResult#getCount()} elements matching the request
      */
     List<T> getResults();

    /**
      * Tries to access the first element of the result list.
      * Use case: query by slug which should contain zero or one element in the result list.
      * @return the first value or absent
      */
    default Optional<T> head() {
        final List<T> results = getResults();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
     }

    /**
     * Calculates the page number of the result.
     *
     * @return the page number of the result, 0 if the page size is 0, meaning "{@code getCount()}" returns 0.
     */
    default Long getPageIndex() {

        if (getCount() == 0)
            return 0L;

        return Math.min(getOffset() / getCount(), getTotalPages());
    }

    /**
     * Calculates the total number of pages matching the request.
     *
     * @return the total number of pages , 0 if the page size is 0, meaning "{@code getCount()}" returns 0.
     */
    default Long getTotalPages() {

        if (getCount() == 0)
            return 0L;

        return (long) Math.ceil(((double) getTotal()) / getCount());
    }

    /**
      * Checks if this is the first page of a result.
      * @return true if offset is 0 otherwise false
      */
    default boolean isFirst() {
         if (getOffset() == null) {
             throw new UnsupportedOperationException("Can only be used if the offset is known.");
         }
         return getOffset() == 0;
     }

    /**
      * Checks if it is the last possible page.
      * @return true if doing a request with an incremented offset parameter would cause an empty result otherwise false.
      */
     default boolean isLast() {
         if (getOffset() == null || getTotal() == null) {
             throw new UnsupportedOperationException("Can only be used if the offset & total is known.");
         }
         //currently counting the total amount is performed in a second database call, so it is possible
         //that the left side can be greater than total
         return getOffset() + getCount() >= getTotal();
     }
}
