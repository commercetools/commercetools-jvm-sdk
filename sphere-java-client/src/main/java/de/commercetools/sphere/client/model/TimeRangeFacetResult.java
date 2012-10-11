package de.commercetools.sphere.client.model;

import net.jcip.annotations.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.FluentIterable;

import java.util.List;

/** Information about a date range facet, returned as a part of {@link SearchResult}. */
@Immutable
public class TimeRangeFacetResult implements FacetResult {
    private ImmutableList<TimeRangeFacetItem> items;

    /** A list of individual items for this date range facet and their respective counts. */
    public List<TimeRangeFacetItem> getItems() {
        return items;
    }

    /** Parses times returned by the backend as milliseconds into joda.LocalTime instances. */
    static TimeRangeFacetResult fromMilliseconds(RangeFacetResult facetResult) {
        return new TimeRangeFacetResult(
                FluentIterable.from(facetResult.getItems()).transform(TimeRangeFacetItem.fromMilliseconds).toImmutableList());
    }

    private TimeRangeFacetResult(ImmutableList<TimeRangeFacetItem> items) {
        this.items = items;
    }
}
