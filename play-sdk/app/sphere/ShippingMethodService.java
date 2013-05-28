package sphere;

import java.util.Currency;
import io.sphere.client.shop.model.ShippingMethod;
import com.neovisionaries.i18n.CountryCode;

/** Sphere HTTP API for querying shipping methods in a given project.  The zone references of the shipping method
 * query results are expanded. */
public interface ShippingMethodService {
    /** Finds a shipping method by id. */
    FetchRequest<ShippingMethod> byId(String id);

    /** Queries all shipping methods in current project. */
    QueryRequest<ShippingMethod> all();

    /** Queries all shipping methods for a specific location. */
    public QueryRequest<ShippingMethod> byLocation(CountryCode country, String state, Currency currency);

    /** Queries all shipping methods for a specific location. */
    public QueryRequest<ShippingMethod> byLocation(CountryCode country, Currency currency);

}
