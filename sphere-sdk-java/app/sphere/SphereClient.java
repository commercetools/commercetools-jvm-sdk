package sphere;

import de.commercetools.sphere.client.shop.ShopClient;

/** Provides access to Sphere HTTP APIs. */
// NOTE/TODO: This class is temporary and supposed to be completely merged into the ShopClient of the sphere-java-client!
public class SphereClient {

    // package private (for tests)
    SphereClient(ShopClient shopClient, ClientCredentials clientCredentials, Products products,
            ProductTypes productDefinitions, Categories categories) {
        this.shopClient = shopClient;
        this.clientCredentials = clientCredentials;
        this.products = products;
        this.productDefinitions = productDefinitions;
        this.categories = categories;
    }

    /** OAuth client credentials for the current project. */
    private final ClientCredentials clientCredentials;

    /** Sphere backend HTTP APIs for Products. */
    public final Products products;

    /** Sphere backend HTTP APIs for Product definitions. */
    public final ProductTypes productDefinitions;

    /** Sphere backend HTTP APIs for categories. */
    public final Categories categories;

    /** The underlying SphereShopClient. */
    public final ShopClient shopClient;
}
