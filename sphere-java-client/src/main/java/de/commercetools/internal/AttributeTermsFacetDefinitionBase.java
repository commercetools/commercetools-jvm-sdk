package de.commercetools.internal;

import de.commercetools.sphere.client.model.TermsFacetItem;
import static de.commercetools.internal.util.QueryStringConstruction.*;

import java.util.Map;

public abstract class AttributeTermsFacetDefinitionBase extends AttributeFacetDefinitionBase {
    protected AttributeTermsFacetDefinitionBase(String attribute) {
        super(attribute);
    }

    protected AttributeTermsFacetDefinitionBase(String attribute, String queryParam) {
        super(attribute, queryParam);
    }

    public String getSelectLink(TermsFacetItem item, Map<String, String[]> queryParams) {
        return addStringParam(item.getValue(), queryParam, queryParams);
    }
    public String getUnselectLink(TermsFacetItem item, Map<String, String[]> queryParams) {
        return removeStringParam(item.getValue(), queryParam, queryParams);
    }
    public boolean isSelected(TermsFacetItem item, Map<String, String[]> queryParams) {
        return containsStringParam(item.getValue(), queryParam, queryParams);
    }
}
