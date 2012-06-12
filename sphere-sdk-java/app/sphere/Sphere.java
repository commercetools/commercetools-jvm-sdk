package sphere;

/** Provides access to Sphere HTTP APIs. */
public class Sphere {

    private Sphere() {}
    private static final Sphere instance = new Sphere();
    /** Returns singleton instance of the Sphere class. */
    public static Sphere getInstance() { return instance; }

    /** Sphere backend HTTP APIs for Products. */
    public Products products = new sphere.extra.Products(Config.projectName());

    /** Sphere backend HTTP APIs for Product definitions. */
    public ProductDefinitions productDefinitions = new sphere.extra.ProductDefinitions(Config.projectName());

    /** Sphere backend HTTP APIs for categories. */
    public Categories categories = new sphere.extra.Categories(Config.projectName());
}
