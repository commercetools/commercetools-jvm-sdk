package de.commercetools.sphere.client.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;
import java.util.ArrayList;

/** Information about a terms facet, returned as a part of {@link SearchQueryResult}. */
public class TermsFacetResult implements FacetResult {
    @JsonProperty("missing")
    private int missingValueCount;
    @JsonProperty("total")
    private int presentValueCount;
    @JsonProperty("other")
    private int notReturnedCount;
    private List<TermCount> terms = new ArrayList<TermCount>();

    // for JSON deserializer
    private TermsFacetResult() {}

    /** The number of resources in the search result that have no value for this facet. */
    public int getMissingValueCount() {
        return missingValueCount;
    }

    /** The number of resources in the search result that have some value for this facet. */
    public int getPresentValueCount() {
        return presentValueCount;
    }

    /** The number of resources that have some value for the facet but have not been returned. */
    public int getNotReturnedCount() {
        return notReturnedCount;
    }

    /** A list of individual values for this facet and their respective counts. */
    public List<TermCount> getTerms() {
        return terms;
    }
}
