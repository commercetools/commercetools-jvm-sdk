package de.commercetools.sphere.client.shop;

import com.ning.http.client.AsyncHttpClient;
import de.commercetools.sphere.client.SphereClient;
import net.jcip.annotations.*;

@ThreadSafe
final public class ShopClient implements SphereClient {
    private final ShopClientConfig config;
    private final AsyncHttpClient httpClient;

    public ShopClient(AsyncHttpClient httpClient, ShopClientConfig config) {
        this.httpClient = httpClient;
        this.config = config;
    }

    @Override public ShopClientConfig getConfig() { return this.config; }

    // /** Gets a ProductService providing access to the products of the shop. */
    // ProductService getProductService();
    //
    // /** Gets a ProductDefinitionService providing access to the product deifnitions of the shop. */
    // ProductDefinitionService getProductDefinitionService();
    //
    // /** Gets a CategoryService providing access to the product categories of shop. */
    // CategoryService getCategoryService();
}
