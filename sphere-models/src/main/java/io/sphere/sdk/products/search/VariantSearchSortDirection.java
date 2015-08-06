package io.sphere.sdk.products.search;

import io.sphere.sdk.search.SearchSortDirection;

/**
 * Since the value of a specific attribute can vary across the variants of a single product, one value must be chosen from all its variants.
 * The sort direction defines which attribute value is selected for a product.
 *
 * <p>Notice this changed behaviour must only be used when sorting attributes belonging to variants, not products.</p>
 */
public enum VariantSearchSortDirection implements SearchSortDirection {
    /** When the sort direction is ascending, the minimum value is used. **/
    ASC,
    /** When the direction is descending, the maximum value is used. */
    DESC,
    /** Changes the default behaviour of the ascending sort by using the maximum value instead. */
    ASC_MAX,
    /** Changes the default behaviour of the descending sort by using the minimum value instead. */
    DESC_MIN
}