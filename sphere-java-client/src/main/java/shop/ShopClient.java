package de.commercetools.sphere.client.shop;

import com.ning.http.client.AsyncHttpClient;
import de.commercetools.sphere.client.SphereClient;
import net.jcip.annotations.*;

@ThreadSafe
final public class ShopClient implements SphereClient {
    private final ShopClientConfig config;
    private final ClientCredentials credentials;
    private final Products products;
    private final Categories categories;

    public ShopClient(ShopClientConfig config, ClientCredentials credentials, Products products, Categories categories) {
        this.config = config;
        this.credentials = credentials;
        this.products = products;
        this.categories = categories;
    }

    @Override public ShopClientConfig getConfig() { return this.config; }

    /** Gets a ProductService providing access to the products of the shop. */
    public Products getProducts() { return products; }
    
    /** Gets a CategoryService providing access to the product categories of shop. */
    public Categories getCategories() { return categories; }
}
