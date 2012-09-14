package sphere;

import com.ning.http.client.AsyncHttpClient;
import org.codehaus.jackson.type.TypeReference;
import java.util.concurrent.TimeUnit;

import de.commercetools.internal.*;
import de.commercetools.sphere.client.*;
import de.commercetools.sphere.client.shop.*;
import de.commercetools.sphere.client.util.*;
import de.commercetools.sphere.client.oauth.OAuthClient;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import de.commercetools.sphere.client.shop.oauth.ShopClientCredentials;
import de.commercetools.sphere.client.model.SearchResult;

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
            final AsyncHttpClient httpClient = new AsyncHttpClient();
            ShopClientConfig config = Config.root().shopClientConfig();
            ShopClientCredentials clientCredentials = ShopClientCredentials.create(config, new OAuthClient(httpClient));
            clientCredentials.refreshAsync().get(30, TimeUnit.SECONDS);
            ProjectEndpoints projectEndpoints = Endpoints.forProject(config.getCoreHttpServiceUrl(), config.getProjectKey());
            RequestBuilderFactory requestBuilderFactory = new RequestBuilderFactoryImpl(httpClient);
            SearchRequestBuilderFactory searchRequestBuilderFactory = new SearchRequestBuilderFactoryImpl(httpClient);
            return new ShopClient(
                config,
                clientCredentials,
                new DefaultProducts(requestBuilderFactory, searchRequestBuilderFactory, projectEndpoints, clientCredentials),
                new DefaultCategories(requestBuilderFactory, projectEndpoints, clientCredentials)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
