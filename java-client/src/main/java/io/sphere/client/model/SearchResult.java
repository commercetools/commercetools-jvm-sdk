package io.sphere.client.model;

import io.sphere.client.SphereClientException;
import io.sphere.client.facets.*;
import io.sphere.client.model.facets.*;
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
    // Conversion to correct facet type
    // --------------------------------------------------------------

    // Terms

    /** Finds a facet result for a terms facet. */
    public TermFacetResult getFacet(TermFacet facet) {
        return getTermsFacet(facet.getAttributeName());
    }

    // Ranges

    /** Finds a facet result for a number range facet. */
    public NumberRangeFacetResult getNumberRangeFacet(RangeFacet facet) {
        return NumberRangeFacetResult.fromBackendDoubles(getRangeFacet(facet.getAttributeName()));
    }

    /** Finds a facet result for a Money range facet. */
    public MoneyRangeFacetResult getMoneyRangeFacet(MoneyRangeFacet facet) {
        return MoneyRangeFacetResult.fromCents(getRangeFacet(facet.getAttributeName()));
    }

    /** Finds a facet result for a DateTime range facet. */
    public DateTimeRangeFacetResult getDateTimeRangeFacet(DateTimeRangeFacet facet) {
        return DateTimeRangeFacetResult.fromMilliseconds(getRangeFacet(facet.getAttributeName()));
    }

    // --------------------------------------------------------------
    // Low-level API for returning the raw facets parsed from JSON.
    // We might decide to make it public when needed.
    // --------------------------------------------------------------

    /** Returns all the facet results exactly in the format as they were returned by the backend.
     * This is a fairly low-level method that should be needed only if you need to do custom facet results manipulation. */
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

    // ----------------------------
    // Helpers
    // ----------------------------

    /** Before downcasting, checks that the type of result is correct. */
    private void checkCorrectType(String attributeName, Class<?> expectedClass, FacetResult facetResult) {
        if (!(expectedClass.isInstance(facetResult))) {
            throw new SphereClientException(attributeName + " is a " + facetResult.getClass().getSimpleName() + ", not " + expectedClass.getSimpleName());
        }
    }
}
