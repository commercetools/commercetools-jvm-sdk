package io.sphere.client.facets;

import io.sphere.client.model.SearchResult;
import io.sphere.client.model.facets.MoneyRangeFacetItem;
import io.sphere.client.model.facets.MoneyRangeFacetResult;

/** Money range facet component. */
public interface MoneyRangeFacet extends Facet<MoneyRangeFacetItem> {
    /** Returns the results for this facet.  */
    public <T> MoneyRangeFacetResult getResult(SearchResult<T> searchResult);
}
