package sphere;

import com.ning.http.client.AsyncHttpClient;
import de.commercetools.internal.*;
import de.commercetools.internal.oauth.ShopClientCredentials;
import de.commercetools.internal.request.ProductRequestFactoryImpl;
import de.commercetools.internal.request.RequestFactory;
import de.commercetools.internal.request.RequestFactoryImpl;
import de.commercetools.sphere.client.Endpoints;
import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.oauth.OAuthClient;
import de.commercetools.sphere.client.shop.CategoryTree;
import de.commercetools.sphere.client.shop.ShopClient;
import de.commercetools.sphere.client.shop.ShopClientConfig;
import net.jcip.annotations.GuardedBy;

/** Provides configured and initialized instance of {@link SphereClient}. */
public class Sphere {
    private Sphere() {}
    private static Object clientLock = new Object();
    @GuardedBy("sphereClientLock")
    private static SphereClient client;

    /** Returns a thread-safe client for accessing the Sphere APIs.
     *  The returned instance is designed to be shared by all controllers in your application. */
    public static SphereClient getClient() {
        SphereClient result = client;
        if (result == null) {
            synchronized (clientLock) {
                result = client;
                if (result == null) {
                    client = result = createSphereClient();
                    initChaosLevel();
                }
            }
        }
        return result;
    }

    /** Initializes the static chaos level. The chaos level is a static variable so we don't have to pass it around
     * everywhere, and it's the only exception where we do this. */
    private static void initChaosLevel() {
        ChaosMode.setChaosLevel(SphereConfig.root().chaosLevel());
    }

    /** Top-level DI entry point where everything gets wired together. */
    private static SphereClient createSphereClient() {
        try {
            SphereConfig config = SphereConfig.root();
            return new SphereClient(SphereConfig.root(), ShopClient.create(config.createShopClientConfig()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
