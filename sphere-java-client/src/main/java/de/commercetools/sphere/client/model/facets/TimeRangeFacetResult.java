package de.commercetools.sphere.client.model.facets;

import net.jcip.annotations.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.FluentIterable;

import java.util.List;

/** Aggregated counts for a time range facet, returned as a part of {@link de.commercetools.sphere.client.model.SearchResult}. */
@Immutable
public class TimeRangeFacetResult implements FacetResult {
    private ImmutableList<TimeRangeFacetItem> items;

    /** A list of individual items for this date range facet and their respective counts. */
    public List<TimeRangeFacetItem> getItems() {
        return items;
    }

    /** Parses times returned by the backend as milliseconds into joda.LocalTime instances. */
    public static TimeRangeFacetResult fromMilliseconds(RangeFacetResult facetResult) {
        return new TimeRangeFacetResult(
                FluentIterable.from(facetResult.getItems()).transform(TimeRangeFacetItem.fromMilliseconds).toImmutableList());
    }

    private TimeRangeFacetResult(ImmutableList<TimeRangeFacetItem> items) {
        this.items = items;
    }
}
