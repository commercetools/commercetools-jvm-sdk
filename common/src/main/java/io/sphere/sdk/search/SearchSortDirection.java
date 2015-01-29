package io.sphere.sdk.search;

/**
 * Since the value of a specific attribute can vary across the variants of a single product one value must be chosen from all its variants.
 * The sort direction defines which attribute value is selected for a product:
 * - ASC: When the sort direction is ascending, the minimum value is used.
 * - DESC: When the direction is descending, the maximum value is used.
 * - ASC_MAX: Changes the default behaviour of the ascending sort by using the maximum value instead.
 * - DESC_MIN: Changes the default behaviour of the descending sort by using the minimum value instead.
 * Notice this changed behaviour must only be used when sorting attributes belonging to variants, not products.
 */
public enum SearchSortDirection {
    ASC, DESC,
    // TODO Allow these values only for product attributes
    ASC_MAX,
    DESC_MIN
}