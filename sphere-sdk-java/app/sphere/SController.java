package sphere;

/** Base controller. Provides shared Sphere instance. */
public class SController extends play.mvc.Controller {
    protected static Sphere sphere = Sphere.getInstance();
}
