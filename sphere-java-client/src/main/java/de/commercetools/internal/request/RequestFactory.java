package de.commercetools.internal.request;

import de.commercetools.internal.command.Command;
import de.commercetools.sphere.client.FetchRequest;
import de.commercetools.sphere.client.filters.expressions.FilterExpression;
import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.SearchRequest;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.model.SearchResult;
import org.codehaus.jackson.type.TypeReference;
import de.commercetools.sphere.client.CommandRequest;

/** Creates instances of requests. Allows for mocking in tests. */
public interface RequestFactory {
    /** Creates a request that fetches a single object. */
    <T> FetchRequest<T> createFetchRequest(String url, TypeReference<T> jsonParserTypeRef);

    /** Creates a request that fetches a single object,
     * handling given HTTP status code by returning {@link com.google.common.base.Optional#absent()}. */
    <T> FetchRequest<T> createFetchRequestWithErrorHandling(
            String url, int handledErrorStatus, TypeReference<T> jsonParserTypeRef);

    /** Creates a request that queries for multiple objects. */
    <T> QueryRequest<T> createQueryRequest(String url, TypeReference<QueryResult<T>> jsonParserTypeRef);

    /** Creates a request that uses search to query for multiple objects. */
    <T> SearchRequest<T> createSearchRequest(
            String url, Iterable<FilterExpression> filters, TypeReference<SearchResult<T>> jsonParserTypeRef);

    /** Creates a request that issues a command to be executed. */
    <T> CommandRequest<T> createCommandRequest(String url, Command command, TypeReference<T> jsonParserTypeRef);

    /** Creates a request that issues a command to be executed,
     *  handling given HTTP status code by returning {@link com.google.common.base.Optional#absent()}. */
    <T> CommandRequestWithErrorHandling<T> createCommandRequestWithErrorHandling(
            String url, Command command, int handledErrorStatus, TypeReference<T> jsonParserTypeRef);
}
