package sphere;

import io.sphere.client.shop.ShopClient;
import io.sphere.internal.ChaosMode;
import net.jcip.annotations.GuardedBy;

/** Provides configured and initialized instance of {@link SphereClient}. */
public class Sphere {
    private Sphere() {}
    private static Object clientLock = new Object();
    @GuardedBy("sphereClientLock")
    private static volatile SphereClient client;

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
