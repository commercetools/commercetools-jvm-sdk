package de.commercetools.sphere.client;

import java.util.Map;

public interface FilterDefinition {
    Filter parse(Map<String,String[]> queryString);
}
