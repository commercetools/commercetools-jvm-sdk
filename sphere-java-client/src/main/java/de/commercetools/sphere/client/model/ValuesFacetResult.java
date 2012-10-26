package de.commercetools.sphere.client.model;

/** Aggregated counts for a value facet, returned as a part of {@link SearchResult}. */
public class ValuesFacetResult implements FacetResult {
    private int count;

    // for JSON deserializer
    private ValuesFacetResult() {}

    public int getCount() {
        return count;
    }
}
