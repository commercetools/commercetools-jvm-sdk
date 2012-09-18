package de.commercetools.internal;

import de.commercetools.sphere.client.oauth.ClientCredentials;
import de.commercetools.sphere.client.util.RequestBuilder;
import de.commercetools.sphere.client.util.SearchRequestBuilder;
import de.commercetools.sphere.client.util.CommandRequestBuilder;
import de.commercetools.sphere.client.model.SearchResult;
import com.ning.http.client.AsyncHttpClient;
import net.jcip.annotations.Immutable;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;

/** Creates request builders that do real HTTP request. Can be mocked in tests. */
@Immutable
public class RequestFactoryImpl implements RequestFactory {
    private final AsyncHttpClient httpClient;
    private final ClientCredentials credentials;

    public RequestFactoryImpl(AsyncHttpClient httpClient, ClientCredentials credentials) {
        this.httpClient = httpClient;
        this.credentials = credentials;
    }

    public <T> RequestHolder<T> createGetRequest(String url) {
        return new RequestHolderImpl<T>(SetCredentials.forRequest(httpClient.prepareGet(url), credentials));
    }

    public <T> RequestHolder<T> createPostRequest(String url) {
        return new RequestHolderImpl<T>(SetCredentials.forRequest(httpClient.preparePost(url), credentials));
    }

    public <T> RequestBuilder<T> createQueryRequest(String url, TypeReference<T> jsonParserTypeRef) {
        return new RequestBuilderImpl<T>(this.<T>createGetRequest(url), jsonParserTypeRef);
    }

    public <T> SearchRequestBuilder<T> createSearchRequest(
            String fullTextQuery, String url, TypeReference<SearchResult<T>> jsonParserTypeRef) {
        return new SearchRequestBuilderImpl<T>(fullTextQuery, this.<SearchResult<T>>createGetRequest(url), jsonParserTypeRef);
    }

    public <T> CommandRequestBuilder<T> createCommandRequest(String url, Command command, TypeReference<T> jsonParserTypeRef) {
        return new CommandRequestBuilderImpl<T>(this.<T>createPostRequest(url), command, jsonParserTypeRef);
    }
}
