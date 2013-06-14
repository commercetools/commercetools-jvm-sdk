package io.sphere.internal.facets;

import io.sphere.client.QueryParam;
import io.sphere.client.facets.TermFacet;
import io.sphere.client.model.SearchResult;
import io.sphere.client.model.facets.TermFacetItem;
import io.sphere.client.model.facets.TermFacetResult;

import static io.sphere.internal.util.ListUtil.*;

import java.util.List;

/** Provides implementation of getUrlParams for term facets (term facets behave essentially the same for any attribute type). */
public abstract class AttributeTermFacetBase extends FacetBase<TermFacetItem> implements TermFacet {
    protected AttributeTermFacetBase(String attribute) {
        super(attribute);
    }

    @Override public List<QueryParam> getUrlParams(TermFacetItem item) {
        return list(new QueryParam(queryParam, item.getValue()));
    }

    @Override public <T> TermFacetResult getResult(SearchResult<T> searchResult) { return searchResult.getTermFacet(this); }
}
