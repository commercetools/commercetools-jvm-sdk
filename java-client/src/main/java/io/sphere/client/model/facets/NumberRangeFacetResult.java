package io.sphere.client.model.facets;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import net.jcip.annotations.Immutable;

import java.util.List;

/** Aggregated counts for a number range facet,
 * returned as a part of {@link io.sphere.client.model.SearchResult}. */
@Immutable
public class NumberRangeFacetResult implements FacetResult {
    private ImmutableList<NumberRangeFacetItem> items;

    /** A list of individual ranges for this money range facet and their respective counts. */
    public List<NumberRangeFacetItem> getItems() {
        return items;
    }

    /** Parses cent amounts returned by the backend into BigDecimals representing money amounts. */
    public static NumberRangeFacetResult fromBackendDoubles(RangeFacetResultRaw facetResult) {
        if (facetResult == null) return null;
        return new NumberRangeFacetResult(
                FluentIterable.from(facetResult.getItems()).transform(NumberRangeFacetItem.fromBackendDoubles).toImmutableList());
    }

    private NumberRangeFacetResult(ImmutableList<NumberRangeFacetItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "NumberRangeFacetResult{" +
                "items=" + items +
                '}';
    }
}
