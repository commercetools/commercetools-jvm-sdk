package sphere;

import de.commercetools.internal.Categories;
import de.commercetools.sphere.client.shop.*;
import net.jcip.annotations.ThreadSafe;
import play.mvc.Http;

import java.util.Currency;

/** Client for accessing all Sphere APIs for building a store.
 *  To obtain an instance of this class designed to be shared by all the controllers in your application,
 *  use the static method {@link Sphere#getClient}. */
@ThreadSafe
public class SphereClient {

    private final ShopClient underlyingClient;
    private final Currency shopCurrency;

    /** API for fetching and searching Products. */
    public final Products products;

    /** API for fetching categories. */
    public final CategoryTree categories;

    /** API for fetching orders. */
    public final Orders orders;

    SphereClient(Config config, ShopClient shopClient) {
        this.underlyingClient = shopClient;
        shopCurrency = config.shopCurrency();
        products = underlyingClient.products();
        categories = underlyingClient.categories();
        orders = underlyingClient.orders();
    }

    /** API for working with cart bound to the current request. */
    public CurrentCart currentCart() {
        return new CurrentCart(
                Http.Context.current().session(),
                this.underlyingClient.carts(),
                shopCurrency);
    }
}
