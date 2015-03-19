package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

public class FilteredFacetResult extends Base implements FacetResult {
    private final long count;

    private FilteredFacetResult(final long count) {
        this.count = count;
    }

    /**
     * The number of resources matching the filter value.
     * @return amount of resources matching the filter value.
     */
    public long getCount() {
        return count;
    }

    public static FilteredFacetResult of(final long count) {
        return new FilteredFacetResult(count);
    }
}
