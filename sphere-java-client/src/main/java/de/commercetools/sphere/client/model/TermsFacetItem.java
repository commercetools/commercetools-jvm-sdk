package de.commercetools.sphere.client.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Map;

/** Count of resources for an individual value of a terms facet ({@link TermsFacetResult}). */
public class TermsFacetItem implements FacetResultItem {
    @JsonProperty("term")
    private String value;
    private int count;

    // for JSON deserializer
    private TermsFacetItem() {}

    /** The value, e.g. 'blue'. */
    public String getValue() {
        return value;
    }

    /** Count of resources that have this value,
     * e.g. 5 if there were 5 resources with color 'blue' (given that the terms facet was 'color'). */
    public int getCount() {
        return count;
    }
}
