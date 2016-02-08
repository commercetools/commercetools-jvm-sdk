package io.sphere.sdk.search;

public enum SearchSortDirection {
    /**
     * On multi-valued attributes, when the sort direction is ascending, the minimum value is used.
     */
    ASC,

    /**
     * On multi-valued attributes, when the direction is descending, the maximum value is used.
     */
    DESC,

    /**
     * Changes the default behaviour of the ascending sort by using the maximum value instead.
     * USE ONLY ON MULTI-VALUED ATTRIBUTES, such as those at product variant level!
     */
    ASC_MAX,

    /**
     * Changes the default behaviour of the descending sort by using the minimum value instead.
     * USE ONLY ON MULTI-VALUED ATTRIBUTES, such as those at product variant level!
     */
    DESC_MIN;

    @Override
    public String toString() {
        return this.name().toLowerCase().replace("_", ".");
    }
}