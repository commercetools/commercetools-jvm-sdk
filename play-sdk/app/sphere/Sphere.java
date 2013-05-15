package sphere;

import io.sphere.client.shop.SphereClient;
import io.sphere.internal.ChaosMode;
import io.sphere.internal.util.Util;
import net.jcip.annotations.GuardedBy;

/** Provides configured and initialized instance of {@link sphere.SphereClient}. */
public class Sphere {
    private Sphere() {}
    private static Object clientLock = new Object();
    @GuardedBy("clientLock")
    private static volatile sphere.SphereClient client;

    /** Returns a thread-safe client for accessing the Sphere APIs.
     *  The returned instance is designed to be shared by all controllers in your application. */
    public static sphere.SphereClient getClient() {
        sphere.SphereClient result = client;
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
    private static sphere.SphereClient createSphereClient() {
        try {
            SphereConfig config = SphereConfig.root();
            return new sphere.SphereClient(SphereConfig.root(), SphereClient.create(config.createSphereClientConfig()));
        } catch (Exception e) {
            throw Util.toSphereException(e);
        }
    }
}
