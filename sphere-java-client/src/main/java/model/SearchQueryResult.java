package de.commercetools.sphere.client.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/** Result of a search query to the Sphere backend. */
public class SearchQueryResult<T> {
    private int offset;
    private int count;
    private int total;
    private List<T> results;
    private Map<String, String> facets;

    public SearchQueryResult(int offset, int count, int total, Collection<T> results, Map<String, String> facets) {
        this.offset = offset;
        this.count = count;
        this.total = total;
        this.results = new ArrayList<T>(results);
        this.facets = facets;
    }

    // for JSON deserializer
    private SearchQueryResult() { }

    public int getOffset() {
        return offset;
    }
    public int getCount() {
        return count;
    }
    public int getTotal() {
        return total;
    }
    public List<T> getResults() {
        return results;
    }
    public Map<String, String> getFacets() {
        return facets;
    }
}
