package sphere;

import com.ning.http.client.AsyncHttpClient;
import sphere.util.OAuthClient;

import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.Endpoints;
import de.commercetools.sphere.client.shop.ShopClient;
import de.commercetools.sphere.client.shop.DefaultProducts;
import de.commercetools.sphere.client.shop.DefaultCategories;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/** Provides default configured and initialized instance of {@link ShopClient}.
 *  The instance is designed to be shared by all controllers in your application. */
public class Sphere {
    /** This is a static class. */
    private Sphere() {}

    private final static ShopClient shopClient = createClient();

    /** Returns a thread-safe client for accessing the Sphere APIs. */
    public static ShopClient getShopClient() { return shopClient; }

    private static ShopClient createClient() throws RuntimeException {
        try {
            AsyncHttpClient httpClient = new AsyncHttpClient();
            Config config = Config.root();
            ClientCredentialsImpl clientCredentials = ClientCredentialsImpl.create(config, new OAuthClient(httpClient));
            clientCredentials.refreshAsync().get(30, TimeUnit.SECONDS);
            ProjectEndpoints projectEndpoints = Endpoints.forProject(config.coreEndpoint(), config.projectID());
            return new ShopClient(
                config.shopClientConfig(),
                clientCredentials,
                new DefaultProducts(httpClient, clientCredentials, projectEndpoints),
                new DefaultCategories(httpClient, clientCredentials, projectEndpoints)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
