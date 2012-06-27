package sphere;

/** Provides access to Sphere HTTP APIs. */
public class Sphere {

    private static Sphere instance;
    /** Returns singleton instance of the Sphere class. */
    public static synchronized Sphere getInstance() { return instance; }
    /** Only designed as static because Play's controllers are static. */
    static synchronized void setInstance(Sphere sphere) { instance = sphere; }

    /** Only designed as static because Play's controllers are static. */
    public static void initializeInstance() {
        ClientCredentials clientCredentials = ClientCredentials.create(Config.root());
        clientCredentials.refreshAsync().get();
        // Make the instance available to Play's static controllers.
        Sphere.setInstance(new Sphere(Config.root(), clientCredentials));
    }

    private Config config;
    Sphere(Config config, ClientCredentials clientCredentials) {
        this.config = config;
        this.projectCredentials = clientCredentials;
        project = config.projectID();
        ProjectEndpoints projectEndpoints = Endpoints.forProject(config.coreEndpoint(), project);
        products = new DefaultProducts(project, projectCredentials, projectEndpoints);
        productDefinitions = new DefaultProductDefinitions(project, projectCredentials, projectEndpoints);
        categories = new DefaultCategories(project, projectCredentials, projectEndpoints);
    }

    /** OAuth client credentials for the current project. */
    private ClientCredentials projectCredentials;

    /** Current project, configured in application.conf under the key 'sphere.project'. */
    private String project;

    /** Sphere backend HTTP APIs for Products. */
    public Products products;

    /** Sphere backend HTTP APIs for Product definitions. */
    public ProductDefinitions productDefinitions;

    /** Sphere backend HTTP APIs for categories. */
    public Categories categories;
}
