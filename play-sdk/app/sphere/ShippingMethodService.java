package sphere;

import java.util.Currency;
import java.util.List;
import io.sphere.client.shop.model.Location;
import io.sphere.client.shop.model.ShippingMethod;

/** Sphere HTTP API for querying shipping methods in a given project.  The zone references of the shipping method
 * query results are expanded. */
public interface ShippingMethodService {
    /** Finds a shipping method by id. */
    FetchRequest<ShippingMethod> byId(String id);

    /** Queries all shipping methods in current project. */
    QueryRequest<ShippingMethod> all();

    /** Fetches all shipping methods for a specific location. */
    public FetchRequest<List<ShippingMethod>> forLocation(Location location, Currency currency);

    /** Fetches all shipping methods for a cart in the current project. The cart must have
     * a shipping address set. */
    public FetchRequest<List<ShippingMethod>> forCart(String cartId);    
}
