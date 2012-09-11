package de.commercetools.sphere.client.util;

import org.codehaus.jackson.type.TypeReference;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import de.commercetools.sphere.client.model.SearchResult;

/** Creates concrete implementations of {@link SearchRequestBuilder}. */
public interface SearchRequestBuilderFactory {
    <T> SearchRequestBuilder<T> create(String fullTextQuery, String url, ClientCredentials credentials, TypeReference<SearchResult<T>> jsonParserTypeRef);
}
