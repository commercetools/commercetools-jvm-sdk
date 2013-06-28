package io.sphere.internal.request;

import com.google.common.base.Optional;
import io.sphere.internal.command.Command;
import io.sphere.client.FetchRequest;
import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.QueryRequest;
import io.sphere.client.SearchRequest;
import io.sphere.client.model.QueryResult;
import io.sphere.client.model.SearchResult;
import io.sphere.client.shop.ApiMode;
import org.codehaus.jackson.type.TypeReference;
import io.sphere.client.CommandRequest;

import java.util.Locale;

/** Creates instances of requests. Allows for mocking in tests. */
public interface RequestFactory {

    // -----------------
    // Read
    // -----------------

    /** Creates a request that fetches a single object. */
    <T> FetchRequest<T> createFetchRequest(String url, Optional<ApiMode> apiMode, TypeReference<T> jsonParserTypeRef);

    /** Creates a request that fetches a single object,
     *  handling given HTTP error status code by returning {@link com.google.common.base.Optional#absent()}. */
    <T> FetchRequest<T> createFetchRequestWithErrorHandling(
            String url, Optional<ApiMode> apiMode, int handledErrorStatus, TypeReference<T> jsonParserTypeRef);

    /** Creates a request that fetches a single object, implemented using a query endpoint. */
    <T> FetchRequest<T> createFetchRequestBasedOnQuery(String url, Optional<ApiMode> apiMode, TypeReference<QueryResult<T>> jsonParserTypeRef);

    /** Creates a request that queries for multiple objects. */
    <T> QueryRequest<T> createQueryRequest(String url, Optional<ApiMode> apiMode, TypeReference<QueryResult<T>> jsonParserTypeRef);

    /** Creates a request that uses search to query for multiple objects. */
    <T> SearchRequest<T> createSearchRequest(
            String url, Optional<ApiMode> apiMode, Iterable<FilterExpression> filters, TypeReference<SearchResult<T>> jsonParserTypeRef, Locale locale);

    // -----------------
    // Write
    // -----------------

    /** Creates a request that issues a command to be executed. */
    <T> CommandRequest<T> createCommandRequest(String url, Command command, TypeReference<T> jsonParserTypeRef);
}
