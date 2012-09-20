package de.commercetools.sphere.client.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/** Result of a search query to the Sphere backend. */
public class SearchResult<T> {
    private int offset;
    private int count;
    private int total;
    private List<T> results;
    @JsonProperty("facets")
    private Map<String, FacetResult> facets;

    public SearchResult(int offset, int count, int total, Collection<T> results, Map<String, FacetResult> facets) {
        this.offset = offset;
        this.count = count;
        this.total = total;
        this.results = new ArrayList<T>(results);
        this.facets = facets;
    }

    // for JSON deserializer
    private SearchResult() { }

    /** Gets a terms facet result for given facet expression. */
    public TermsFacetResult getTermsFacet(String expression) {
        return (TermsFacetResult)facets.get(expression);
    }

    /** Gets a range facet result for given facet expression. */
    public RangeFacetResult getRangeFacet(String expression) {
        return (RangeFacetResult)facets.get(expression);
    }

    /** Gets a date range facet result for given facet expression. */
    public DateRangeFacetResult getDateRangeFacet(String expression) {
        // Search returns Date facet ranges in milliseconds
        return DateRangeFacetResult.fromMilliseconds(getRangeFacet(expression));
    }

    /** Gets a time range facet result for given facet expression. */
    public TimeRangeFacetResult getTimeRangeFacet(String expression) {
        // Search returns Time facet ranges in milliseconds
        return TimeRangeFacetResult.fromMilliseconds(getRangeFacet(expression));
    }

    /** Gets a time range facet result for given facet expression. */
    public DateTimeRangeFacetResult getDateTimeRangeFacet(String expression) {
        // Search returns DateTime facet ranges in milliseconds
        return DateTimeRangeFacetResult.fromMilliseconds(getRangeFacet(expression));
    }

    // TODO value facets

    public int getOffset() { return offset; }
    public int getCount() { return count; }
    public int getTotal() { return total; }
    public List<T> getResults() { return results; }
}
