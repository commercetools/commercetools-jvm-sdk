package de.commercetools.sphere.client.model.facets;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/** Aggregated counts for a numeric range facet, returned as a part of {@link de.commercetools.sphere.client.model.SearchResult}. */
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
