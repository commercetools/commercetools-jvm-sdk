package de.commercetools.sphere.client.model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;
import java.util.ArrayList;

/** Aggregated counts for a terms facet, returned as a part of {@link SearchResult}. */
public class TermsFacetResult implements FacetResult {
    private List<TermsFacetItem> items = new ArrayList<TermsFacetItem>();
    private final int missingValuesCount;
    private final int presentValuesCount;
    private final int notReturnedValuesCount;

    @JsonCreator
    public TermsFacetResult(
            @JsonProperty("terms") List<TermsFacetItem> items,
            @JsonProperty("missing") int missingValuesCount,
            @JsonProperty("total") int presentValuesCount,
            @JsonProperty("other") int notReturnedValuesCount)
    {
        this.items = items;
        this.missingValuesCount = missingValuesCount;
        this.presentValuesCount = presentValuesCount;
        this.notReturnedValuesCount = notReturnedValuesCount;
    }

    /** The number of resources in the search result that have no value for this facet. */
    public int getMissingValueCount() {
        return missingValuesCount;
    }

    /** The number of resources in the search result that have some value for this facet. */
    public int getPresentValueCount() {
        return presentValuesCount;
    }

    /** The number of resources that have some value for the facet but have not been returned. */
    public int getNotReturnedCount() {
        return notReturnedValuesCount;
    }

    /** A list of individual values for this facet and their respective counts. */
    public List<TermsFacetItem> getItems() {
        return items;
    }
}
