package de.commercetools.internal.facets;

import de.commercetools.sphere.client.QueryParam;
import de.commercetools.sphere.client.model.facets.FacetItem;

import static de.commercetools.internal.util.SearchUtil.*;

import java.util.List;

/** Provides implementation of getUrlParams for term facets (term facets behave essentially the same for any attribute type). */
public abstract class AttributeTermsFacetBase extends FacetBase<FacetItem> {
    protected AttributeTermsFacetBase(String attribute) {
        super(attribute);
    }

    @Override public List<QueryParam> getUrlParams(FacetItem item) {
        return list(new QueryParam(queryParam, item.getValue()));
    }
}
