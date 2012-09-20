package sphere;

/** Base controller for all controllers using the Sphere backend.
 *  Provides shared Sphere instance. */
public class ShopController extends play.mvc.Controller {
    /** Singleton instance of the Sphere client. */
    protected static final SphereClient sphere = Sphere.getSphereClient();
}
