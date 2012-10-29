package sphere;

import de.commercetools.sphere.client.facets.Facet;
import de.commercetools.sphere.client.facets.FacetParser;
import de.commercetools.sphere.client.facets.expressions.FacetExpression;
import de.commercetools.sphere.client.filters.Filter;
import de.commercetools.sphere.client.filters.FilterParser;
import play.mvc.Controller;

import de.commercetools.sphere.client.filters.expressions.FilterExpression;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/** Base controller for all controllers using the Sphere backend.
 *  Provides shared Sphere instance. */
public class ShopController extends Controller {
    /** Singleton instance of the Sphere client. */
    protected static final SphereClient sphere = Sphere.getClient();

    protected static List<FilterExpression> parseFilters(Map<String,String[]> queryString, Collection<Filter> filters) {
        return FilterParser.parse(queryString, filters);
    }

    protected static List<FacetExpression> parseFacets(Map<String,String[]> queryString, Collection<Facet> facets) {
        return FacetParser.parse(queryString, facets);
    }
}
