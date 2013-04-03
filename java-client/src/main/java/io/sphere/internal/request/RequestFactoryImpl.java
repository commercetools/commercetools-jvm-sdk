package io.sphere.internal.request;

import io.sphere.internal.command.Command;
import io.sphere.client.FetchRequest;
import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.model.QueryResult;
import io.sphere.client.oauth.ClientCredentials;
import io.sphere.client.QueryRequest;
import io.sphere.client.SearchRequest;
import io.sphere.client.model.SearchResult;
import com.ning.http.client.AsyncHttpClient;
import io.sphere.client.shop.ApiMode;
import net.jcip.annotations.Immutable;
import org.codehaus.jackson.type.TypeReference;
import io.sphere.client.CommandRequest;

/** Creates specialized requests based on basic GET and POST requests. */
@Immutable
public class RequestFactoryImpl implements RequestFactory {
    private final BasicRequestFactory basic;
    public RequestFactoryImpl(BasicRequestFactory basic) {
        this.basic = basic;
    }

    @Override public <T> FetchRequest<T> createFetchRequest(
            String url, TypeReference<T> jsonParserTypeRef) {
        return new FetchRequestImpl<T>(basic.<T>createGet(url), jsonParserTypeRef);
    }

    @Override public <T> FetchRequest<T> createFetchRequestWithErrorHandling(
            String url, int handledErrorStatus, TypeReference<T> jsonParserTypeRef) {
        return new FetchRequestWithErrorHandling<T>(basic.<T>createGet(url), handledErrorStatus, jsonParserTypeRef);
    }

    @Override public <T> FetchRequest<T> createFetchRequestBasedOnQuery(
            String url, TypeReference<QueryResult<T>> jsonParserTypeRef) {
        return new FetchRequestBasedOnQuery<T>(createQueryRequest(url, jsonParserTypeRef));
    }

    @Override public <T> QueryRequest<T> createQueryRequest(
            String url, TypeReference<QueryResult<T>> jsonParserTypeRef) {
        return new QueryRequestImpl<T>(basic.<QueryResult<T>>createGet(url), jsonParserTypeRef);
    }

    @Override public <T> SearchRequest<T> createSearchRequest(
            String url, ApiMode apiMode, Iterable<FilterExpression> filters, TypeReference<SearchResult<T>> jsonParserTypeRef) {
        return new SearchRequestImpl<T>(
                withApiMode(basic.<SearchResult<T>>createGet(url), apiMode),
                jsonParserTypeRef
        ).filter(filters);
    }

    @Override public <T> CommandRequest<T> createCommandRequest(
            String url, Command command, TypeReference<T> jsonParserTypeRef) {
        return new CommandRequestImpl<T>(basic.<T>createPost(url), command, jsonParserTypeRef);
    }

    @Override public <T> CommandRequestWithErrorHandling<T> createCommandRequestWithErrorHandling(
            String url, Command command, int handledErrorStatus, TypeReference<T> jsonParserTypeRef) {
        return new CommandRequestWithErrorHandling<T>(basic.<T>createPost(url), command, handledErrorStatus, jsonParserTypeRef);
    }

    // -----------------------------
    // API mode (staging / live)
    // -----------------------------

    private <T> RequestHolder<T> withApiMode(RequestHolder<T> requestHolder, ApiMode apiMode) {
        return requestHolder.addQueryParameter("staged", apiMode == ApiMode.Staging ? "true" : "false");
    }
}
