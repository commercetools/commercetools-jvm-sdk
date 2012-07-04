package sphere;

import sphere.util.OAuthClient;

import java.util.concurrent.TimeUnit;

/** Provides access to Sphere HTTP APIs. */
public class Sphere {

    private final static Sphere instance = create();

    /** Returns singleton instance of the Sphere class. */
    public static Sphere getInstance() { return instance; }
    
    private static Sphere create() {
        Config config = Config.root();
        ClientCredentials clientCredentials = ClientCredentials.create(config, new OAuthClient());
        Validation<Void> refreshedTokens = clientCredentials.refreshAsync().getWrappedPromise().await(60, TimeUnit.SECONDS).get();   // HACK TEMP because of tests
        // TODO switch to Futures API that rethrows exceptions (next Play release?)
        if (refreshedTokens.isError()) {
            throw new RuntimeException(refreshedTokens.getError().getMessage());
        }
        ProjectEndpoints projectEndpoints = Endpoints.forProject(config.coreEndpoint(), config.projectID());
        Sphere sphere = new Sphere(
            clientCredentials,
            new DefaultProducts(clientCredentials, projectEndpoints),
            new DefaultProductDefinitions(clientCredentials, projectEndpoints),
            new DefaultCategories(clientCredentials, projectEndpoints)
        );
        // Make the instance available to Play's static controllers.
        return sphere;
    }

    private Sphere(ClientCredentials clientCredentials, Products products, ProductDefinitions productDefinitions, Categories categories) {
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
