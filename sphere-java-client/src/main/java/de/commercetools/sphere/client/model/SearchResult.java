package de.commercetools.sphere.client.model;

import de.commercetools.internal.Defaults;
import de.commercetools.sphere.client.facets.*;
import de.commercetools.sphere.client.model.facets.*;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.*;

/** Result of a search query to the Sphere backend. */
public class SearchResult<T> {
    private int offset;
    private int count;
    private int total;
    private List<T> results = new ArrayList<T>();
    @JsonProperty("facets")
    private Map<String, FacetResult> facets = new HashMap<String, FacetResult>();
    private int pageSize = 1;

    public int getOffset() { return offset; }
    public int getCount() { return count; }
    public int getTotal() { return total; }
    public List<T> getResults() { return results; }
    public int getPageSize() { return pageSize; }

    public SearchResult(int offset, int count, int total, Collection<T> results, Map<String, FacetResult> facets, int pageSize) {
        this.offset = offset;
        this.count = count;
        this.total = total;
        this.results = (results != null) ? new ArrayList<T>(results) : null;
        this.facets = facets;
        this.pageSize = pageSize;
    }

    // for JSON deserializer
    private SearchResult() {
    }

    /** Returns the index of the current page, starting at zero. */
    public int getCurrentPage() {
        return offset / pageSize;
    }

    /** Returns the total number of pages. */
    public int getTotalPages() {
        return (total + pageSize - 1) / pageSize;
    }

    // --------------------------------------------------------------
    // Returning correct facet type based on facet type
    // --------------------------------------------------------------

    public TermFacetResult getFacet(TermFacet facet) {
        return getTermsFacet(facet.getAttributeName());
    }

    public RangeFacetResult getFacet(RangeFacet facet) {
        return getRangeFacet(facet.getAttributeName());
    }

    public DateRangeFacetResult getFacet(DateRangeFacet facet) {
        return getDateRangeFacet(facet.getAttributeName());
    }

    public TimeRangeFacetResult getFacet(TimeRangeFacet facet) {
        return getTimeRangeFacet(facet.getAttributeName());
    }

    public DateTimeRangeFacetResult getFacet(DateTimeRangeFacet facet) {
        return getDateTimeRangeFacet(facet.getAttributeName());
    }

    // --------------------------------------------------------------
    // Low-level API for returning the raw facets parsed from JSON.
    // We might decide to make it public when needed.
    // --------------------------------------------------------------

    public Map<String, FacetResult> getFacetsRaw() {
        return facets;
    }

    private FacetResult getFacetRaw(String expression) {
        return facets.get(expression);
    }

    /** Gets a terms facet result for given facet expression. */
    private TermFacetResult getTermsFacet(String expression) {
        FacetResult facetResult = getFacetRaw(expression);
        if (facetResult == null)
            return null;
        checkCorrectType(expression, TermFacetResult.class, facetResult);
        return (TermFacetResult)facetResult;
    }

    /** Gets a range facet result for given facet expression. */
    private RangeFacetResult getRangeFacet(String expression) {
        FacetResult facetResult = getFacetRaw(expression);
        if (facetResult == null)
            return null;
        checkCorrectType(expression, RangeFacetResult.class, facetResult);
        return (RangeFacetResult)facetResult;
    }

    /** Gets a values facet result for given facet expression. */
    private ValueFacetResult getValueFacet(String expression) {
        String prefix = expression + Defaults.valueFacetAliasSeparator;
        int prefixLen = prefix.length();
        List<ValueFacetItem> facetItems = new ArrayList<ValueFacetItem>();
        for (Map.Entry<String, FacetResult> facetResultEntry: getFacetsRaw().entrySet()) {
            if (facetResultEntry.getKey().startsWith(prefix)) {
                facetItems.add(new ValueFacetItem(
                        facetResultEntry.getKey().substring(prefixLen),
                        ((ValueFacetResultRaw)facetResultEntry.getValue()).getCount()));
            }
        }
        return new ValueFacetResult(facetItems);
    }

    // ----------------------------
    // Helpers
    // ----------------------------

    /** Gets a date range facet result for given facet expression. */
    private DateRangeFacetResult getDateRangeFacet(String expression) {
        // Search returns facets in milliseconds
        return DateRangeFacetResult.fromMilliseconds(getRangeFacet(expression));
    }

    /** Gets a time range facet result for given facet expression. */
    private TimeRangeFacetResult getTimeRangeFacet(String expression) {
        // Search returns facets in milliseconds
        return TimeRangeFacetResult.fromMilliseconds(getRangeFacet(expression));
    }

    /** Gets a time range facet result for given facet expression. */
    private DateTimeRangeFacetResult getDateTimeRangeFacet(String expression) {
        // Search returns facets in milliseconds
        return DateTimeRangeFacetResult.fromMilliseconds(getRangeFacet(expression));
    }

    /** Before downcasting, checks that the type of result is correct. */
    private void checkCorrectType(String attributeName, Class<?> expectedClass, FacetResult facetResult) {
        if (!(expectedClass.isInstance(facetResult))) {
            throw new RuntimeException(attributeName + " is a " + facetResult.getClass().getSimpleName() + ", not " + expectedClass.getSimpleName());
        }
    }
}
