package sphere;

import sphere.util.OAuthClient;

import java.util.concurrent.TimeUnit;

/** Provides access to Sphere HTTP APIs. */
public class Sphere {

    private static Sphere instance;
    /** Returns singleton instance of the Sphere class. */
    public static synchronized Sphere getInstance() { return instance; }

    /** Initializes the Sphere singleton instance. */
    // central dependency wiring point
    public static synchronized void initializeInstance() {
        Config config = Config.root();
        ClientCredentials clientCredentials = ClientCredentials.create(config, new OAuthClient());
        clientCredentials.refreshAsync().getWrappedPromise().await(60, TimeUnit.SECONDS).get();   // HACK TEMP because of tests
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
