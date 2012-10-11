package de.commercetools.sphere.client;

import de.commercetools.sphere.client.model.TermsFacetItem;

import java.util.Map;

public interface TermsFacetDefinition extends FacetDefinition {
    String getSelectLink(TermsFacetItem item, Map<String, String[]> queryParams);
    String getUnselectLink(TermsFacetItem item, Map<String, String[]> queryParams);
    boolean isSelected(TermsFacetItem item, Map<String, String[]> queryParams);
}