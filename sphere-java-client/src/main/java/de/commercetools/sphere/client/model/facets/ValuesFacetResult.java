package de.commercetools.sphere.client.model.facets;

import com.google.common.collect.ImmutableList;
import de.commercetools.internal.util.SearchUtil;

import java.util.List;

/** Aggregated counts for a values facet, returned as a part of {@link de.commercetools.sphere.client.model.SearchResult}. */
public class ValuesFacetResult implements FacetResult {
    private ImmutableList<FacetItem> items;

    public ValuesFacetResult(Iterable<FacetItem> items) {
        this.items = SearchUtil.toList(items);
    }

    /** A list of individual values for this facet and their respective counts. */
    public List<FacetItem> getItems() {
        return items;
    }
}
