package sphere;

import sphere.util.OAuthClient;

import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.Endpoints;
import de.commercetools.sphere.client.shop.ShopClient;
import de.commercetools.sphere.client.shop.DefaultProducts;
import de.commercetools.sphere.client.shop.DefaultCategories;

import java.util.concurrent.TimeUnit;

/** Provides default configured and initialized instance of {@link ShopClient}.
 *  The instance is designed to be shared by all controllers in your application. */
public class Sphere {
    /** This is a static class. */
    private Sphere() {}

    private final static ShopClient shopClient = createClient();

    /** Returns a thread-safe client for accessing the Sphere APIs. */
    public static ShopClient getShopClient() { return shopClient; }

    private static ShopClient createClient() {
        Config config = Config.root();
        ClientCredentialsImpl clientCredentials = ClientCredentialsImpl.create(config, new OAuthClient());
        Validation<Void> tokenResult = clientCredentials.refreshAsync().getWrappedPromise().await(60, TimeUnit.SECONDS).get(); // HACK TEMP because of tests
        // TODO switch to Futures API that rethrows exceptions (next Play release?)
        if (tokenResult.isError()) {
            throw new RuntimeException(tokenResult.getError().getMessage());
        }
        ProjectEndpoints projectEndpoints = Endpoints.forProject(config.coreEndpoint(), config.projectID());

        return new ShopClient(
            config.shopClientConfig(),
            clientCredentials,
            new DefaultProducts(clientCredentials, projectEndpoints),
            new DefaultCategories(clientCredentials, projectEndpoints)
        );
    }
}
