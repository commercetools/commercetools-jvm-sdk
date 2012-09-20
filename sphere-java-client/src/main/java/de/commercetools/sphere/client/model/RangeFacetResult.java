package de.commercetools.sphere.client.model;

import java.util.List;

/** Information about a range facet, returned as a part of {@link SearchResult}. */
public class RangeFacetResult implements FacetResult {
    private List<RangeCount> ranges;

    // for JSON deserializer
    private RangeFacetResult() {}

    /** A list of individual ranges for this range facet and their respective counts. */
    public List<RangeCount> getRanges() {
        return ranges;
    }
}
