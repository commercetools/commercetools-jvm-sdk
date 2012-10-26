package de.commercetools.sphere.client.model;

import de.commercetools.sphere.client.facets.*;
import de.commercetools.sphere.client.model.facets.*;
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
    // Returning correct facet type based on facet type
    // --------------------------------------------------------------

    public TermsFacetResult getFacet(TermsFacet facet) {
        return getTermsFacetRaw(facet.getAttributeName());
    }

    public RangeFacetResult getFacet(RangeFacet facet) {
        return getRangesFacetRaw(facet.getAttributeName());
    }

    public DateRangeFacetResult getFacet(DateRangeFacet facet) {
        return getDateRangeFacetRaw(facet.getAttributeName());
    }

    public TimeRangeFacetResult getFacet(TimeRangeFacet facet) {
        return getTimeRangeFacetRaw(facet.getAttributeName());
    }

    public DateTimeRangeFacetResult getFacet(DateTimeRangeFacet facet) {
        return getDateTimeRangeFacetRaw(facet.getAttributeName());
    }

    /** Gets generic facet result for given facet.
     *  This is a low-level method. You will have to downcast to process the result.
     *  Please refer to {@link #getFacet} if you know the actual facet type. */
    public FacetResult getFacetGeneric(Facet facet) {
        return getFacetRaw(facet.getAttributeName());
    }

    // --------------------------------------------------------------
    // Low-level API for returning the raw facets parsed from JSON.
    // We might decide to make it public.
    // --------------------------------------------------------------

    public FacetResult getFacetRaw(String expression) {
        return facets.get(expression);
    }

    /** Gets a terms facet result for given facet expression. */
    private TermsFacetResult getTermsFacetRaw(String expression) {
        FacetResult facetResult = getFacetRaw(expression);
        if (facetResult == null)
            return null;
        checkCorrectType(expression, TermsFacetResult.class, facetResult);
        return (TermsFacetResult)facetResult;
    }

    /** Gets a range facet result for given facet expression. */
    private RangeFacetResult getRangesFacetRaw(String expression) {
        FacetResult facetResult = getFacetRaw(expression);
        if (facetResult == null)
            return null;
        checkCorrectType(expression, RangeFacetResult.class, facetResult);
        return (RangeFacetResult)facetResult;
    }

    /** Gets a values facet result for given facet expression. */
    private ValuesFacetResult getValuesFacetRaw(String expression) {
        FacetResult facetResult = getFacetRaw(expression);
        if (facetResult == null)
            return null;
        checkCorrectType(expression, ValuesFacetResult.class, facetResult);
        return (ValuesFacetResult)facetResult;
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
    private void checkCorrectType(String attributeName, Class<?> expectedClass, FacetResult facetResult) {
        if (!(expectedClass.isInstance(facetResult))) {
            throw new RuntimeException(attributeName + " is a " + facetResult.getClass().getSimpleName() + ", not " + expectedClass.getSimpleName());
        }
    }
}
