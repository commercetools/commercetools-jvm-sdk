package de.commercetools.sphere.client;

import de.commercetools.sphere.client.model.RangeFacetItem;

import java.util.Map;

public interface RangeFacetDefinition extends FacetDefinition {
    String getSelectLink(RangeFacetItem item, Map<String, String[]> queryParams);
    String getUnselectLink(RangeFacetItem item, Map<String, String[]> queryParams);
    boolean isSelected(RangeFacetItem item, Map<String, String[]> queryParams);
}