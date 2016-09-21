package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

public final class FilteredFacetResult extends Base implements FacetResult {
    private final Long count;
    @Nullable
    private final Long productCount;

    @JsonCreator
    private FilteredFacetResult(final Long count, @Nullable final Long productCount) {
        this.count = count;
        this.productCount = productCount;
    }

    /**
     * The number of variants matching the filter value.
     * @return amount of variants matching the filter value.
     */
    public Long getCount() {
        return count;
    }

    /**
     * The number of products matching the filter value.
     * @return amount of products matching the filter value.
     */
    @Nullable
    public Long getProductCount() {
        return productCount;
    }

    public static FilteredFacetResult of(final Long count) {
        return new FilteredFacetResult(count, null);
    }

    public static FilteredFacetResult of(final Long count, final Long productCount) {
        return new FilteredFacetResult(count, productCount);
    }
}
