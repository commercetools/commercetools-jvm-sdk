package de.commercetools.internal;

import de.commercetools.sphere.client.FilterExpression;
import de.commercetools.sphere.client.RequestBuilder;
import de.commercetools.sphere.client.SearchRequestBuilder;
import de.commercetools.sphere.client.util.CommandRequestBuilder;
import de.commercetools.sphere.client.model.SearchResult;
import org.codehaus.jackson.type.TypeReference;

import java.util.Collection;

/** Creates instances of request builders. Allows for mocking in tests. */
public interface RequestFactory {
    /** Creates a request to a query endpoint that parses the response into a given type. */
    <T> RequestBuilder<T> createQueryRequest(String url, TypeReference<T> jsonParserTypeRef);

    /** Creates a search request that parses the response into a given type. */
    <T> SearchRequestBuilder<T> createSearchRequest(
            String url, Collection<FilterExpression> filters, TypeReference<SearchResult<T>> jsonParserTypeRef);

    /** Creates a request to a query endpoint that parses the response into a given type. */
    <T> CommandRequestBuilder<T> createCommandRequest(String url, Command command, TypeReference<T> jsonParserTypeRef);
}
