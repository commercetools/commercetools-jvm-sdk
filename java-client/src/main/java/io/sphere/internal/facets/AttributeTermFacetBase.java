package io.sphere.internal.facets;

import io.sphere.client.QueryParam;
import io.sphere.client.model.facets.TermFacetItem;

import static io.sphere.internal.util.ListUtil.*;

import java.util.List;

/** Provides implementation of getUrlParams for term facets (term facets behave essentially the same for any attribute type). */
public abstract class AttributeTermFacetBase extends FacetBase<TermFacetItem> {
    protected AttributeTermFacetBase(String attribute) {
        super(attribute);
    }

    @Override public List<QueryParam> getUrlParams(TermFacetItem item) {
        return list(new QueryParam(queryParam, item.getValue()));
    }
}
