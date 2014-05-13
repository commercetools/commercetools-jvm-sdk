package io.sphere.client.facets;

import io.sphere.client.model.SearchResult;
import io.sphere.client.model.facets.NumberRangeFacetResult;
import io.sphere.client.model.facets.NumberRangeFacetItem;

/** Number range facet component. */
public interface RangeFacet extends Facet<NumberRangeFacetItem> {
    /** Returns the results for this facet.  */
    public <T> NumberRangeFacetResult getResult(SearchResult<T> searchResult);
}
