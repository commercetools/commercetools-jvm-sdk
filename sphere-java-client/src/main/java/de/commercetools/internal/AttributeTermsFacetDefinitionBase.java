package de.commercetools.internal;

import de.commercetools.sphere.client.QueryParam;
import de.commercetools.sphere.client.model.TermsFacetItem;
import static de.commercetools.internal.util.SearchUtil.*;

import java.util.List;

public abstract class AttributeTermsFacetDefinitionBase extends CustomAttributeFacetDefinitionBase<TermsFacetItem> {
    protected AttributeTermsFacetDefinitionBase(String attribute) {
        super(attribute);
    }

    protected AttributeTermsFacetDefinitionBase(String attribute, String queryParam) {
        super(attribute, queryParam);
    }

    @Override
    public List<QueryParam> getUrlParams(TermsFacetItem item) {
        return list(new QueryParam(queryParam, item.getValue()));
    }
}
