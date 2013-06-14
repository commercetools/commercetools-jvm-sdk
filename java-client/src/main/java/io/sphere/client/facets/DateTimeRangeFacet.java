package io.sphere.client.facets;

import io.sphere.client.model.SearchResult;
import io.sphere.client.model.facets.DateTimeRangeFacetItem;
import io.sphere.client.model.facets.DateTimeRangeFacetResult;

/** Datetime range facet component. */
public interface DateTimeRangeFacet extends Facet<DateTimeRangeFacetItem> {
    /** Returns the results for this facet.  */
    public <T> DateTimeRangeFacetResult getResult(SearchResult<T> searchResult);
}

