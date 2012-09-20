package de.commercetools.sphere.client.model;

import net.jcip.annotations.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.FluentIterable;

import java.util.List;
import java.util.ArrayList;

/** Information about a date range facet, returned as a part of {@link SearchResult}. */
@Immutable
public class DateTimeRangeFacetResult implements FacetResult {
    private ImmutableList<DateTimeRangeCount> ranges;

    /** A list of individual ranges for this date range facet and their respective counts. */
    public List<DateTimeRangeCount> getRanges() {
        return ranges;
    }

    private DateTimeRangeFacetResult(ImmutableList<DateTimeRangeCount> ranges) {
        this.ranges = ranges;
    }

    /** Parses dates returned by the backend as milliseconds into joda.LocalDate instances. */
    static DateTimeRangeFacetResult fromMilliseconds(RangeFacetResult facetResult) {
        return new DateTimeRangeFacetResult(
                FluentIterable.from(facetResult.getRanges()).transform(DateTimeRangeCount.fromMilliseconds).toImmutableList());
    }
}
