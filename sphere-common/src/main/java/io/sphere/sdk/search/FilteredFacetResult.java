package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

public final class FilteredFacetResult extends Base implements FacetResult {
    private final Long count;

    @JsonCreator
    private FilteredFacetResult(final Long count) {
        this.count = count;
    }

    /**
     * The number of resources matching the filter value.
     * @return amount of resources matching the filter value.
     */
    public Long getCount() {
        return count;
    }

    public static FilteredFacetResult of(final Long count) {
        return new FilteredFacetResult(count);
    }
}
