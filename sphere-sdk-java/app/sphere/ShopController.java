package sphere;

import de.commercetools.sphere.client.shop.ShopClient;

/** Base controller for all controllers using the Sphere backend.
 *  Provides shared Sphere instance. */
public class ShopController extends play.mvc.Controller {
    /** Singleton instance of the Sphere client. */
    protected static final ShopClient sphere = Sphere.getShopClient();
}
