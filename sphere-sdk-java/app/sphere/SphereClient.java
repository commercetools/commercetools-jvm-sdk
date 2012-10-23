package sphere;

import de.commercetools.sphere.client.shop.*;
import net.jcip.annotations.ThreadSafe;
import play.mvc.Http;

import java.util.Currency;

@ThreadSafe
public class SphereClient {
    private final ShopClient underlyingClient;
    private final Currency shopCurrency;

    SphereClient(Config config, ShopClient shopClient) {
        this.underlyingClient = shopClient;
        products = underlyingClient.products();
        categories = underlyingClient.categories();
        orders = underlyingClient.orders();
        shopCurrency = config.shopCurrency();
    }

    /** API for fetching and searching Products. */
    public final Products products;

    /** API for fetching categories. */
    public final Categories categories;

    /** API for fetching orders. */
    public final Orders orders;

    /** API for working with cart bound to the current request. */
    public CurrentCart currentCart() {
        return new CurrentCart(
                Http.Context.current().session(),
                this.underlyingClient.carts(),
                shopCurrency);
    }
}
