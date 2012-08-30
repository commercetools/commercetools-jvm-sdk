package sphere;

import com.ning.http.client.AsyncHttpClient;
import de.commercetools.sphere.client.*;
import de.commercetools.sphere.client.shop.*;
import de.commercetools.sphere.client.util.*;
import de.commercetools.sphere.client.oauth.OAuthClient;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import de.commercetools.sphere.client.shop.oauth.ShopClientCredentials;
import java.util.concurrent.TimeUnit;
import org.codehaus.jackson.type.TypeReference;

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
            RequestBuilderFactory requestBuilderFactory = requestBuilderFactory(httpClient);
            SearchRequestBuilderFactory searchRequestBuilderFactory = searchRequestBuilderFactory(httpClient);
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
    
    private static AsyncHttpClient.BoundRequestBuilder setCredentials(AsyncHttpClient.BoundRequestBuilder builder, ClientCredentials credentials) {
        return builder.setHeader("Authorization", "Bearer " + credentials.accessToken());
    }

    // boilerplate for setting up default RequestBuilder (consider using Guice)
    private static RequestBuilderFactory requestBuilderFactory(final AsyncHttpClient httpClient) {
        return new RequestBuilderFactory() {
            public <T> RequestBuilder<T> create(
                    String url, ClientCredentials credentials, TypeReference<T> jsonParserTypeRef) {
                return new RequestBuilderImpl<T>(
                        setCredentials(httpClient.prepareGet(url), credentials), jsonParserTypeRef);
            }
        };
    }

    // boilerplate for setting up default SearchRequestBuilder (consider using Guice)
    private static SearchRequestBuilderFactory searchRequestBuilderFactory(final AsyncHttpClient httpClient) {
        return new SearchRequestBuilderFactory() {
            public <T> SearchRequestBuilder<T> create(
                    String fullTextQuery, String url, ClientCredentials credentials, TypeReference<T> jsonParserTypeRef) {
                return new SearchRequestBuilderImpl<T>(
                        fullTextQuery, setCredentials(httpClient.prepareGet(url), credentials), jsonParserTypeRef);
            }
        };
    }
}
