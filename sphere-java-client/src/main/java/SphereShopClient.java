package de.commercetools.sphere.client;

import com.ning.http.client.AsyncHttpClient;

@net.jcip.annotations.ThreadSafe
final public class SphereShopClient implements SphereClient {
    private final SphereShopClientConfig config;
    private final AsyncHttpClient httpClient;

    public SphereShopClient(AsyncHttpClient httpClient, SphereShopClientConfig config) {
        this.httpClient = httpClient;
        this.config = config;
    }

    @Override public SphereShopClientConfig getConfig() { return this.config; }

    // /** Gets a ProductService providing access to the products of the shop. */
    // ProductService getProductService();
    //
    // /** Gets a ProductDefinitionService providing access to the product deifnitions of the shop. */
    // ProductDefinitionService getProductDefinitionService();
    //
    // /** Gets a CategoryService providing access to the product categories of shop. */
    // CategoryService getCategoryService();
}
