package de.commercetools.internal.request;

import de.commercetools.internal.command.Command;
import de.commercetools.sphere.client.FilterExpression;
import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.SearchRequest;
import de.commercetools.sphere.client.model.SearchResult;
import org.codehaus.jackson.type.TypeReference;
import de.commercetools.sphere.client.CommandRequest;

/** Creates instances of requests. Allows for mocking in tests. */
public interface RequestFactory {
    /** Creates a request to a query endpoint that parses the response into a given type. */
    <T> QueryRequest<T> createQueryRequest(String url, TypeReference<T> jsonParserTypeRef);

    /** Creates a search request that parses the response into a given type. */
    <T> SearchRequest<T> createSearchRequest(
            String url, Iterable<FilterExpression> filters, TypeReference<SearchResult<T>> jsonParserTypeRef);

    /** Creates a request to a query endpoint that parses the response into a given type. */
    <T> CommandRequest<T> createCommandRequest(String url, Command command, TypeReference<T> jsonParserTypeRef);
}
