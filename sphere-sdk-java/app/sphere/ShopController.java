package sphere;

import de.commercetools.sphere.client.*;
import play.mvc.Controller;

import de.commercetools.sphere.client.FilterExpression;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/** Base controller for all controllers using the Sphere backend.
 *  Provides shared Sphere instance. */
public class ShopController extends Controller {
    /** Singleton instance of the Sphere client. */
    protected static final SphereClient sphere = Sphere.getSphereClient();

    protected static List<FilterExpression> parseFilters(Map<String,String[]> queryString, Collection<FilterDefinition> filterDefinitions) {
        return FilterParser.parse(queryString, filterDefinitions);
    }

    protected static List<FacetExpression> parseFacets(Map<String,String[]> queryString, Collection<FacetDefinition> facetDefinitions) {
        return FacetParser.parse(queryString, facetDefinitions);
    }
}
