package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.SphereClient;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import net.jcip.annotations.*;

@ThreadSafe
final public class ShopClient implements SphereClient {
    private final ShopClientConfig config;
    private final Products products;
    private final Categories categories;
    private final Carts carts;

    public ShopClient(ShopClientConfig config, Products products, Categories categories, Carts carts) {
        this.config = config;
        this.products = products;
        this.categories = categories;
        this.carts = carts;
    }

    @Override public ShopClientConfig getConfig() { return this.config; }

    /** Provides access to shop's products. */
    public Products products() { return products; }

    /** Provides access to shop's categories. */
    public Categories categories() { return categories; }

    /** Provides access to shop's shopping carts. */
    public Carts getCarts() { return carts; }
}
