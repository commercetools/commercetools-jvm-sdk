package de.commercetools.sphere.client.model.facets;

import de.commercetools.sphere.client.model.facets.FacetResult;

/** Aggregated counts for a value facet, returned as a part of {@link de.commercetools.sphere.client.model.SearchResult}. */
public class ValuesFacetResultRaw implements FacetResult {
    private int count;

    // for JSON deserializer
    private ValuesFacetResultRaw() {}

    public int getCount() {
        return count;
    }
}
