package sphere;

/** Base controller. Provides shared Sphere instance. */
// Better name: ShopController?
public class SController extends play.mvc.Controller {
    // Better name for the field: shop / shopClient ?
    protected static final SphereClient sphere = Sphere.getShopClient();
}
