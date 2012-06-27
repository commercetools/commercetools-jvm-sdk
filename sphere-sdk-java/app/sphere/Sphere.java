package sphere;

import sphere.util.OAuthClient;

/** Provides access to Sphere HTTP APIs. */
public class Sphere {

    private static Sphere instance;
    /** Returns singleton instance of the Sphere class. */
    public static synchronized Sphere getInstance() { return instance; }

    /** Initializes the Sphere singleton instance. */
    // central dependency wiring point
    public static void initializeInstance() {
        Config config = Config.root();
        ClientCredentials clientCredentials = ClientCredentials.create(config, new OAuthClient());
        clientCredentials.refreshAsync().get();
        ProjectEndpoints projectEndpoints = Endpoints.forProject(config.coreEndpoint(), config.projectID());
        Sphere sphere = new Sphere(
            clientCredentials,
            new DefaultProducts(clientCredentials, projectEndpoints),
            new DefaultProductDefinitions(clientCredentials, projectEndpoints),
            new DefaultCategories(clientCredentials, projectEndpoints)
        );
        // Make the instance available to Play's static controllers.
        instance = sphere;
    }

    public Sphere(ClientCredentials clientCredentials, Products products, ProductDefinitions productDefinitions, Categories categories) {
        this.clientCredentials = clientCredentials;
        this.products = products;
        this.productDefinitions = productDefinitions;
        this.categories = categories;
    }

    /** OAuth client credentials for the current project. */
    private ClientCredentials clientCredentials;

    /** Sphere backend HTTP APIs for Products. */
    public Products products;

    /** Sphere backend HTTP APIs for Product definitions. */
    public ProductDefinitions productDefinitions;

    /** Sphere backend HTTP APIs for categories. */
    public Categories categories;
}
