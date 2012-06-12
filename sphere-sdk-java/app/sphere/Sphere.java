package sphere;

import sphere.util.OAuthCredentials;

/** Provides access to Sphere HTTP APIs. */
public class Sphere {

    private Sphere() {}
    private static final Sphere instance = new Sphere();
    /** Returns singleton instance of the Sphere class. */
    public static Sphere getInstance() { return instance; }

    /** OAuth client credentials for the current project. */
    // Initialization (request to the auth server) starts at the load time of this class. We probably want better control over that.
    private OAuthCredentials projectCredentials = OAuthCredentials.initClientCredentialsAsync(
        Endpoints.tokenEndpoint(),
        Config.projectID(),
        Config.projectSecret()
    );

    /** Current project, configured in application.conf under the key ''. */
    private String project = Config.projectName();

    /** Sphere backend HTTP APIs for Products. */
    public Products products = new sphere.extra.Products(project, projectCredentials);

    /** Sphere backend HTTP APIs for Product definitions. */
    public ProductDefinitions productDefinitions = new sphere.extra.ProductDefinitions(project, projectCredentials);

    /** Sphere backend HTTP APIs for categories. */
    public Categories categories = new sphere.extra.Categories(project, projectCredentials);
}
