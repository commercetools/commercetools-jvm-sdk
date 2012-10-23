package de.commercetools.internal;

import de.commercetools.sphere.client.Filter;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import de.commercetools.sphere.client.RequestBuilder;
import de.commercetools.sphere.client.SearchRequestBuilder;
import de.commercetools.sphere.client.util.CommandRequestBuilder;
import de.commercetools.sphere.client.model.SearchResult;
import com.ning.http.client.AsyncHttpClient;
import net.jcip.annotations.Immutable;
import org.codehaus.jackson.type.TypeReference;

import java.util.Collection;

/** Creates request builders that do real HTTP request. Can be mocked in tests. */
@Immutable
public class RequestFactoryImpl implements RequestFactory {
    private final AsyncHttpClient httpClient;
    private final ClientCredentials credentials;

    public RequestFactoryImpl(AsyncHttpClient httpClient, ClientCredentials credentials) {
        this.httpClient = httpClient;
        this.credentials = credentials;
    }

    protected <T> RequestHolder<T> createGet(String url) {
        return new RequestHolderImpl<T>(SetCredentials.forRequest(httpClient.prepareGet(url), credentials));
    }

    protected <T> RequestHolder<T> createPost(String url) {
        return new RequestHolderImpl<T>(SetCredentials.forRequest(httpClient.preparePost(url), credentials));
    }

    public <T> RequestBuilder<T> createQueryRequest(String url, TypeReference<T> jsonParserTypeRef) {
        return new RequestBuilderImpl<T>(this.<T>createGet(url), jsonParserTypeRef);
    }

    public <T> SearchRequestBuilder<T> createSearchRequest(
            String url, Collection<Filter> filters, TypeReference<SearchResult<T>> jsonParserTypeRef) {
        return new SearchRequestBuilderImpl<T>(filters, this.<SearchResult<T>>createGet(url), jsonParserTypeRef);
    }

    public <T> CommandRequestBuilder<T> createCommandRequest(String url, Command command, TypeReference<T> jsonParserTypeRef) {
        return new CommandRequestBuilderImpl<T>(this.<T>createPost(url), command, jsonParserTypeRef);
    }
}
