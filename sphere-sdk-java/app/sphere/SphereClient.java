package sphere;

/** Provides access to Sphere HTTP APIs. */
public class SphereClient {

    // package private (for tests)
    SphereClient(ClientCredentials clientCredentials, Products products, ProductDefinitions productDefinitions, Categories categories) {
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
    public final ProductDefinitions productDefinitions;

    /** Sphere backend HTTP APIs for categories. */
    public final Categories categories;
}
