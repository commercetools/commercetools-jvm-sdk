package io.sphere.client.facets;

import io.sphere.client.model.SearchResult;
import io.sphere.client.model.facets.TermFacetItem;
import io.sphere.client.model.facets.TermFacetResult;

// TODO distinguish:
//   TermFacet<T> extends Facet<TermFacetItem<T>>.
// Implement for T = {String, Double}
// Now all term facet results are string based.
// (Note: test term number facets with the backend).

/** Term facets component. */
public interface TermFacet extends Facet<TermFacetItem> {
    /** Returns the results for this facet.  */
    public <T> TermFacetResult getResult(SearchResult<T> searchResult);
}
