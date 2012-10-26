package de.commercetools.internal.facets;

import de.commercetools.sphere.client.QueryParam;
import de.commercetools.sphere.client.model.facets.TermsFacetItem;
import static de.commercetools.internal.util.SearchUtil.*;

import java.util.List;

/** Provides implementation of getUrlParams for term facets (term facets behave essentially the same for any attribute type). */
public abstract class AttributeTermsFacetBase extends FacetBase<TermsFacetItem> {
    protected AttributeTermsFacetBase(String attribute) {
        super(attribute);
    }

    @Override public List<QueryParam> getUrlParams(TermsFacetItem item) {
        return list(new QueryParam(queryParam, item.getValue()));
    }
}
