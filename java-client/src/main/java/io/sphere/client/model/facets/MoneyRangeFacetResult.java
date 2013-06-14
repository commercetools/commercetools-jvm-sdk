package io.sphere.client.model.facets;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import net.jcip.annotations.Immutable;

import java.util.List;

/** Aggregated counts for a money range facet (e.g. a price facet or a Money attribute facet),
 * returned as a part of {@link io.sphere.client.model.SearchResult}. */
@Immutable
public class MoneyRangeFacetResult implements FacetResult {
    private ImmutableList<MoneyRangeFacetItem> items;

    /** A list of individual ranges for this money range facet and their respective counts. */
    public List<MoneyRangeFacetItem> getItems() {
        return items;
    }

    /** Parses cent amounts returned by the backend into BigDecimals representing money amounts. */
    public static MoneyRangeFacetResult fromCents(RangeFacetResultRaw facetResult) {
        if (facetResult == null) return null;
        return new MoneyRangeFacetResult(
                FluentIterable.from(facetResult.getItems()).transform(MoneyRangeFacetItem.fromCents).toImmutableList());
    }

    private MoneyRangeFacetResult(ImmutableList<MoneyRangeFacetItem> items) {
        this.items = items;
    }
}
