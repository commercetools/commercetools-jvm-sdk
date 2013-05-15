package sphere;

import io.sphere.client.facets.Facet;
import io.sphere.client.facets.FacetParser;
import io.sphere.client.facets.expressions.FacetExpression;
import io.sphere.client.filters.Filter;
import io.sphere.client.filters.FilterParser;
import play.mvc.Controller;

import io.sphere.client.filters.expressions.FilterExpression;
import play.mvc.Http;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/** Base controller for controllers using the Sphere backend.
 *  Provides a thread-safe instance of the {@link Sphere SphereClient}. */
public class ShopController extends Controller {
    /** Returns a thread-safe instance of the Sphere client. */
    protected static Sphere sphere() {
        return Sphere.getInstance();
    }

    /** Creates filter expressions based on query string of the current request,
     *  ready to be passed to {@link sphere.SearchRequest#filter(Iterable) SearchRequest.filter}. */
    protected static List<FilterExpression> bindFiltersFromRequest(Filter filter) {
        return FilterParser.parse(currentRequest().queryString(), Collections.singletonList(filter));
    }
    /** Creates filter expressions based on query string of the current request,
     *  ready to be passed to {@link sphere.SearchRequest#filter(Iterable) filter}. */
    protected static List<FilterExpression> bindFiltersFromRequest(Collection<Filter> filters) {
        return FilterParser.parse(currentRequest().queryString(), filters);
    }

    /** Creates facet expressions based on query string of the current request,
     *  ready to be passed to {@link sphere.SearchRequest#facet(Iterable) facet}. */
    protected static List<FacetExpression> bindFacetsFromRequest(Facet facet) {
        return FacetParser.parse(currentRequest().queryString(), Collections.singletonList(facet));
    }
    /** Creates facet expressions based on query string of the current request,
     *  ready to be passed to {@link sphere.SearchRequest#facet(Iterable) facet}. */
    protected static List<FacetExpression> bindFacetsFromRequest(Collection<Facet> facets) {
        return FacetParser.parse(currentRequest().queryString(), facets);
    }

    // --------------------
    // Helpers
    // --------------------

    private static Http.Request currentRequest() {
        return Http.Context.current().request();
    }
}
