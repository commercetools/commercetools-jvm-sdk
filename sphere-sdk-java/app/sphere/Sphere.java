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

/**
 * Provides default configured and initialized instance of {@link SphereClient}.
 */
public class Sphere {

    private Sphere() {}
    private final static SphereClient sphereClient = createSphereClient();

    /** Returns a thread-safe client for accessing the Sphere APIs.
     *  The instance is designed to be shared by all controllers in your application. */
    public static SphereClient getClient() {
        return sphereClient;
    }

    /** Top-level DI entry point where everything gets wired together. */
    private static SphereClient createSphereClient() {
        try {
            final AsyncHttpClient httpClient = new AsyncHttpClient();
            ShopClientConfig config = ConfigImpl.root().shopClientConfig();
            ProjectEndpoints projectEndpoints = Endpoints.forProject(config.getCoreHttpServiceUrl(), config.getProjectKey());

            ShopClientCredentials clientCredentials = ShopClientCredentials.createAndBeginRefreshInBackground(config, new OAuthClient(httpClient));
            RequestFactory requestFactory = new RequestFactoryImpl(httpClient, clientCredentials);
            CategoryTree categoryTree = CategoryTreeImpl.createAndBeginBuildInBackground(new CategoriesImpl(requestFactory, projectEndpoints));
            return new SphereClient(
                    ConfigImpl.root(),
                    new ShopClient(
                            config,
                            new ProductServiceImpl(new ProductRequestFactoryImpl(requestFactory, categoryTree), projectEndpoints),
                            categoryTree,
                            new CartServiceImpl(requestFactory, projectEndpoints),
                            new OrderServiceImpl(requestFactory, projectEndpoints),
                            new CustomerServiceImpl(requestFactory, projectEndpoints),
                            new CommentServiceImpl(requestFactory, projectEndpoints),
                            new ReviewServiceImpl(requestFactory, projectEndpoints)
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
