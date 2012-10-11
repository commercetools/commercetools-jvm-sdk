package de.commercetools.sphere.client;

import de.commercetools.sphere.client.model.DateRangeFacetItem;

import java.util.Map;

public interface DateRangeFacetDefinition extends FacetDefinition {
    String getSelectLink(DateRangeFacetItem item, Map<String, String[]> queryParams);
    String getUnselectLink(DateRangeFacetItem item, Map<String, String[]> queryParams);
    boolean isSelected(DateRangeFacetItem item, Map<String, String[]> queryParams);
}

