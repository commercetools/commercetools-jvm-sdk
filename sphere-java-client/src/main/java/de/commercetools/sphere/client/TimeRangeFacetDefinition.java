package de.commercetools.sphere.client;

import de.commercetools.sphere.client.model.TimeRangeFacetItem;

import java.util.Map;

public interface TimeRangeFacetDefinition extends FacetDefinition {
    String getSelectLink(TimeRangeFacetItem item, Map<String, String[]> queryParams);
    String getUnselectLink(TimeRangeFacetItem item, Map<String, String[]> queryParams);
    boolean isSelected(TimeRangeFacetItem item, Map<String, String[]> queryParams);
}

