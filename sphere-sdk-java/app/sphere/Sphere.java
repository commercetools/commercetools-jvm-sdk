package sphere;

import de.commercetools.internal.*;
import de.commercetools.internal.oauth.ShopClientCredentials;
import de.commercetools.internal.request.ProductRequestFactoryImpl;
import de.commercetools.internal.request.RequestFactory;
import de.commercetools.internal.request.RequestFactoryImpl;
import de.commercetools.sphere.client.*;
import de.commercetools.sphere.client.shop.*;
import de.commercetools.sphere.client.oauth.OAuthClient;

import com.ning.http.client.AsyncHttpClient;

/** Provides configured and initialized instance of {@link SphereClient}. */
public class Sphere {
    private Sphere() {}
    private static SphereClient sphereClient;

    /** Returns a thread-safe client for accessing the Sphere APIs.
     *  The instance is designed to be shared by all controllers in your application. */
    public static SphereClient getClient() {
        if (sphereClient == null) {
            synchronized (sphereClient) {
                if (sphereClient == null) {
                    sphereClient = createSphereClient();
                    ChaosMode.setChaosLevel(SphereConfig.root().chaosLevel());
                }
            }
        }
        return sphereClient;
    }

    /** Top-level DI entry point where everything gets wired together. */
    private static SphereClient createSphereClient() {
        try {
            final AsyncHttpClient httpClient = new AsyncHttpClient();
            ShopClientConfig config = SphereConfig.root().createShopClientConfig();
            ProjectEndpoints projectEndpoints = Endpoints.forProject(config.getCoreHttpServiceUrl(), config.getProjectKey());

            ShopClientCredentials clientCredentials = ShopClientCredentials.createAndBeginRefreshInBackground(config, new OAuthClient(httpClient));
            RequestFactory requestFactory = new RequestFactoryImpl(httpClient, clientCredentials);
            CategoryTree categoryTree = CategoryTreeImpl.createAndBeginBuildInBackground(new CategoriesImpl(requestFactory, projectEndpoints));
            return new SphereClient(
                    SphereConfig.root(),
                    new ShopClient(
                            config,
                            new ProductServiceImpl(new ProductRequestFactoryImpl(requestFactory, categoryTree), config.getApiMode(), projectEndpoints),
                            categoryTree,
                            new CartServiceImpl(requestFactory, projectEndpoints),
                            new OrderServiceImpl(requestFactory, projectEndpoints),
                            new CustomerServiceImpl(requestFactory, projectEndpoints),
                            new CommentServiceImpl(requestFactory, projectEndpoints),
                            new ReviewServiceImpl(requestFactory, projectEndpoints),
                            new InventoryServiceImpl(requestFactory, projectEndpoints)
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
