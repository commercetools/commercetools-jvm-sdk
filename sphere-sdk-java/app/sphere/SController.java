package sphere;

/** Base controller. Provides shared Sphere instance. */
// Better name: ShopController?
public class SController extends play.mvc.Controller {
    protected static final SphereClient sphere = Sphere.getShopClient();
}
