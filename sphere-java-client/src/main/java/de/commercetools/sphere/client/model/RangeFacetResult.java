package de.commercetools.sphere.client.model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/** Information about a range facet, returned as a part of {@link SearchResult}. */
public class RangeFacetResult implements FacetResult {
    private final List<RangeFacetItem> items;

    @JsonCreator
    public RangeFacetResult(@JsonProperty("ranges") List<RangeFacetItem> items) {
        this.items = items;
    }

    /** A list of individual ranges for this range facet and their respective counts. */
    public List<RangeFacetItem> getItems() {
        return items;
    }
}
