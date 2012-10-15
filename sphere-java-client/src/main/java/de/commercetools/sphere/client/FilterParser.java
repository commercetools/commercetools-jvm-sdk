package de.commercetools.sphere.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FilterParser {

    public static List<Filter> parse(Map<String,String[]> queryString, Collection<FilterDefinition> filterDefinitions) {
        List<Filter> filterQueries = new ArrayList<Filter>();
        for (FilterDefinition filter: filterDefinitions) {
            filterQueries.add(filter.parse(queryString));
        }
        return filterQueries;
    }
}
