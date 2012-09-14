package de.commercetools.internal;

import com.ning.http.client.AsyncHttpClient;
import org.codehaus.jackson.type.TypeReference;
import de.commercetools.sphere.client.model.SearchResult;
import de.commercetools.sphere.client.util.*;
import de.commercetools.sphere.client.oauth.ClientCredentials;

/**
 * Boilerplate for setting up default SearchRequestBuilder (consider using Guice).
 */
public class SearchRequestBuilderFactoryImpl implements SearchRequestBuilderFactory {
    private final AsyncHttpClient httpClient;

    public SearchRequestBuilderFactoryImpl(AsyncHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public <T> SearchRequestBuilder<T> create(
            String fullTextQuery, String url, ClientCredentials credentials, TypeReference<SearchResult<T>> jsonParserTypeRef) {
        return new SearchRequestBuilderImpl<T>(
                fullTextQuery,
                new RequestHolderImpl<SearchResult<T>>(
                        SetCredentials.forRequest(httpClient.prepareGet(url), credentials)),
                jsonParserTypeRef);
    }
}