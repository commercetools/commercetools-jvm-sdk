package de.commercetools.sphere.client;

import de.commercetools.sphere.client.model.DateTimeRangeFacetItem;

import java.util.Map;

public interface DateTimeRangeFacetDefinition extends FacetDefinition {
    String getSelectLink(DateTimeRangeFacetItem item, Map<String, String[]> queryParams);
    String getUnselectLink(DateTimeRangeFacetItem item, Map<String, String[]> queryParams);
    boolean isSelected(DateTimeRangeFacetItem item, Map<String, String[]> queryParams);
}

