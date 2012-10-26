package de.commercetools.sphere.client.model;

import net.jcip.annotations.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.FluentIterable;

import java.util.List;

/** Aggregated counts for a date range facet, returned as a part of {@link SearchResult}. */
@Immutable
public class DateRangeFacetResult implements FacetResult {
    private ImmutableList<DateRangeFacetItem> items;

    /** A list of individual ranges for this date range facet and their respective counts. */
    public List<DateRangeFacetItem> getItems() {
        return items;
    }

    /** Parses dates returned by the backend as milliseconds into joda.LocalDate instances. */
    static DateRangeFacetResult fromMilliseconds(RangeFacetResult facetResult) {
        if (facetResult == null) return null;
        return new DateRangeFacetResult(
                FluentIterable.from(facetResult.getItems()).transform(DateRangeFacetItem.fromMilliseconds).toImmutableList());
    }

    private DateRangeFacetResult(ImmutableList<DateRangeFacetItem> items) {
        this.items = items;
    }
}
