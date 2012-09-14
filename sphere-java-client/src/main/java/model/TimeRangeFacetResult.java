package de.commercetools.sphere.client.model;

import net.jcip.annotations.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.FluentIterable;

import java.util.List;
import java.util.ArrayList;

/** Information about a date range facet, returned as a part of {@link SearchResult}. */
@Immutable
public class TimeRangeFacetResult implements FacetResult {
    private ImmutableList<TimeRangeCount> ranges;

    /** A list of individual ranges for this date range facet and their respective counts. */
    public List<TimeRangeCount> getRanges() {
        return ranges;
    }

    private TimeRangeFacetResult(ImmutableList<TimeRangeCount> ranges) {
        this.ranges = ranges;
    }

    /** Parses times returned by the backend as milliseconds into joda.LocalTime instances. */
    static TimeRangeFacetResult fromMilliseconds(RangeFacetResult facetResult) {
        return new TimeRangeFacetResult(
                FluentIterable.from(facetResult.getRanges()).transform(TimeRangeCount.fromMilliseconds).toImmutableList());
    }
}
