package sphere;

import play.mvc.Controller;

/** Base controller that provides a shared Sphere instance. */
public class SController extends Controller {
    protected static Sphere sphere = Sphere.getInstance();
}
