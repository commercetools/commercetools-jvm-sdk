package de.commercetools.internal;

import de.commercetools.sphere.client.oauth.ClientCredentials;
import de.commercetools.sphere.client.util.RequestBuilder;
import de.commercetools.sphere.client.util.SearchRequestBuilder;
import de.commercetools.sphere.client.model.SearchResult;
import com.ning.http.client.AsyncHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;

/** Creates request builders that do real HTTP request. Can be mocked in tests. */
public class RequestFactoryImpl implements RequestFactory {
    private final AsyncHttpClient httpClient;

    public RequestFactoryImpl(AsyncHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public <T> RequestHolder<T> createGetRequest(String url, ClientCredentials credentials) {
        return new RequestHolderImpl<T>(SetCredentials.forRequest(httpClient.prepareGet(url), credentials));
    }

    public <T> RequestHolder<T> createPostRequest(String url, ClientCredentials credentials) {
        return new RequestHolderImpl<T>(SetCredentials.forRequest(httpClient.preparePost(url), credentials));
    }

    public <T> RequestBuilder<T> createQueryRequest(String url, ClientCredentials credentials, TypeReference<T> jsonParserTypeRef) {
        return new RequestBuilderImpl<T>(this.<T>createGetRequest(url, credentials), jsonParserTypeRef);
    }

    public <T> SearchRequestBuilder<T> createSearchRequest(
            String fullTextQuery, String url, ClientCredentials credentials, TypeReference<SearchResult<T>> jsonParserTypeRef) {
        return new SearchRequestBuilderImpl<T>(fullTextQuery, this.<SearchResult<T>>createGetRequest(url, credentials), jsonParserTypeRef);
    }

    public <T> RequestHolder<T> createCommandRequest(String url, ClientCredentials credentials, Command command) {
        ObjectWriter jsonWriter = new ObjectMapper().writer();
        try {
          return this.<T>createPostRequest(url, credentials).setBody(jsonWriter.writeValueAsString(command));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
