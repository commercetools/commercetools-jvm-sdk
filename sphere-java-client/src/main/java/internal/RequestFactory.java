package de.commercetools.internal;

import de.commercetools.sphere.client.oauth.ClientCredentials;
import de.commercetools.sphere.client.util.RequestBuilder;
import de.commercetools.sphere.client.util.SearchRequestBuilder;
import de.commercetools.sphere.client.model.SearchResult;
import org.codehaus.jackson.type.TypeReference;

/** Creates instances of request builders. Allows for mocking in tests. */
public interface RequestFactory {
    /** Creates a GET request that parses the backend response into a given type. */
    <T> RequestHolder<T> createGetRequest(String url);

    /** Creates a POST request that parses the backend response into a given type. */
    <T> RequestHolder<T> createPostRequest(String url);

    /** Creates a request to a query endpoint that parses the response into a given type. */
    <T> RequestBuilder<T> createQueryRequest(String url, TypeReference<T> jsonParserTypeRef);

    /** Creates a search request that parses the response into a given type. */
    public <T> SearchRequestBuilder<T> createSearchRequest(
            String fullTextQuery, String url, TypeReference<SearchResult<T>> jsonParserTypeRef);

    /** Creates a request to a query endpoint that parses the response into a given type. */
    <T> RequestHolder<T> createCommandRequest(String url, Command command);
}
