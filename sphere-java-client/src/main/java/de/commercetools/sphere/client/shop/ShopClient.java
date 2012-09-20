package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.SphereClient;
import net.jcip.annotations.*;

@Immutable
@ThreadSafe
final public class ShopClient implements SphereClient {
    private final ShopClientConfig config;
    private final Products products;
    private final Categories categories;
    private final Carts carts;
    private final Orders orders;

    public ShopClient(ShopClientConfig config, Products products, Categories categories, Carts carts, Orders orders) {
        this.config = config;
        this.products = products;
        this.categories = categories;
        this.carts = carts;
        this.orders = orders;
    }

    @Override public ShopClientConfig getConfig() { return this.config; }

    /** Provides access to shop's products. */
    public Products products() { return products; }

    /** Provides access to shop's categories. */
    public Categories categories() { return categories; }

    /** Provides access to shop's shopping carts. */
    public Carts carts() { return carts; }

    /** Provides access to shop's orders. */
    public Orders orders() { return orders; }
}
