package de.commercetools.sphere.client.model;

import de.commercetools.sphere.client.*;
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

    public int getOffset() { return offset; }
    public int getCount() { return count; }
    public int getTotal() { return total; }
    public List<T> getResults() { return results; }

    public SearchResult(int offset, int count, int total, Collection<T> results, Map<String, FacetResult> facets) {
        this.offset = offset;
        this.count = count;
        this.total = total;
        this.results = new ArrayList<T>(results);
        this.facets = facets;
    }

    // for JSON deserializer
    private SearchResult() {}

    // --------------------------------------------------------------
    // Returning correct facet type based on facet definition type
    // --------------------------------------------------------------

    public TermsFacetResult getFacet(TermsFacetDefinition facetDefinition) {
        return getTermsFacetRaw(facetDefinition.getAttributeName());
    }

    public RangeFacetResult getFacet(RangeFacetDefinition facetDefinition) {
        return getRangesFacetRaw(facetDefinition.getAttributeName());
    }

    // TODO value facets
//    public TermsFacetResult getFacet(ValuesFacetDefinition facetDefinition) {
//        return getValuesFacetRaw(facetDefinition.getAttributeName());
//    }

    public DateRangeFacetResult getFacet(DateRangeFacetDefinition facetDefinition) {
        return getDateRangeFacetRaw(facetDefinition.getAttributeName());
    }

    public TimeRangeFacetResult getFacet(TimeRangeFacetDefinition facetDefinition) {
        return getTimeRangeFacetRaw(facetDefinition.getAttributeName());
    }

    public DateTimeRangeFacetResult getFacet(DateTimeRangeFacetDefinition facetDefinition) {
        return getDateTimeRangeFacetRaw(facetDefinition.getAttributeName());
    }

    // --------------------------------------------------------------
    // Low-level API for returning the raw facets parsed from JSON.
    // We might decide to make it public.
    // --------------------------------------------------------------

    /** Gets a terms facet result for given facet expression. */
    private TermsFacetResult getTermsFacetRaw(String expression) {
        FacetResult facetResult = facets.get(expression);
        if (facetResult == null)
            return null;
        checkCorrectType(expression, TermsFacetResult.class, facetResult);
        return (TermsFacetResult)facetResult;
    }

    /** Gets a range facet result for given facet expression. */
    private RangeFacetResult getRangesFacetRaw(String expression) {
        FacetResult facetResult = facets.get(expression);
        if (facetResult == null)
            return null;
        checkCorrectType(expression, RangeFacetResult.class, facetResult);
        return (RangeFacetResult)facetResult;
    }

    /** Gets a date range facet result for given facet expression. */
    private DateRangeFacetResult getDateRangeFacetRaw(String expression) {
        // Search returns facets in milliseconds
        return DateRangeFacetResult.fromMilliseconds(getRangesFacetRaw(expression));
    }

    /** Gets a time range facet result for given facet expression. */
    private TimeRangeFacetResult getTimeRangeFacetRaw(String expression) {
        // Search returns facets in milliseconds
        return TimeRangeFacetResult.fromMilliseconds(getRangesFacetRaw(expression));
    }

    /** Gets a time range facet result for given facet expression. */
    private DateTimeRangeFacetResult getDateTimeRangeFacetRaw(String expression) {
        // Search returns facets in milliseconds
        return DateTimeRangeFacetResult.fromMilliseconds(getRangesFacetRaw(expression));
    }

    /** Before downcasting, checks that the type of result is correct. */
    private void checkCorrectType(String expression, Class<?> expectedClass, FacetResult facetResult) {
        if (!(expectedClass.isInstance(facetResult))) {
            throw new RuntimeException(expression + " is a " + facetResult.getClass().getSimpleName() + ", not " + expectedClass.getSimpleName());
        }
    }
}
