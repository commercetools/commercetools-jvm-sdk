package sphere;

/** Base controller. Provides shared Sphere instance. */
public class SController extends play.mvc.Controller {
    protected static SphereClient sphere = Sphere.getInstance();
}
