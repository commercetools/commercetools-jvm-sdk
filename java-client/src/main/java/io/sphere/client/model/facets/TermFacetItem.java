package io.sphere.client.model.facets;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/** Count of resources for an individual value of a terms facet ({@link TermFacetResult}). */
public class TermFacetItem implements FacetResultItem {
    private String value;
    private int count;

    @JsonCreator
    private TermFacetItem(@JsonProperty("term") String value, @JsonProperty("count") int count) {
        this.value = value;
        this.count = count;
    }

    /** The value, e.g. 'blue'. */
    public String getValue() {
        return value;
    }

    /** Count of resources that have this value,
     *  e.g. 5 if there were 5 resources with color 'blue' (given that the terms facet was 'color'). */
    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "TermFacetItem{" +
                "value='" + value + '\'' +
                ", count=" + count +
                '}';
    }
}
