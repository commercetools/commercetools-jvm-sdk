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

/** Creates requests that make real HTTP calls. Can be mocked in tests. */
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

    @Override public <T> FetchRequest<T> createFetchRequest(
            String url, TypeReference<T> jsonParserTypeRef) {
        return new FetchRequestImpl<T>(this.<T>createGet(url), jsonParserTypeRef);
    }

    @Override public <T> FetchRequest<T> createFetchRequestWithErrorHandling(
            String url, int handledErrorStatus, TypeReference<T> jsonParserTypeRef) {
        return new FetchRequestWithErrorHandling<T>(this.<T>createGet(url), handledErrorStatus, jsonParserTypeRef);
    }

    @Override public <T> FetchRequest<T> createFetchRequestBasedOnQuery(
            String url, TypeReference<QueryResult<T>> jsonParserTypeRef) {
        return new FetchRequestBasedOnQuery<T>(createQueryRequest(url, jsonParserTypeRef));
    }

    @Override public <T> QueryRequest<T> createQueryRequest(
            String url, TypeReference<QueryResult<T>> jsonParserTypeRef) {
        return new QueryRequestImpl<T>(this.<QueryResult<T>>createGet(url), jsonParserTypeRef);
    }

    @Override public <T> SearchRequest<T> createSearchRequest(
            String url, ApiMode apiMode, Iterable<FilterExpression> filters, TypeReference<SearchResult<T>> jsonParserTypeRef) {
        return new SearchRequestImpl<T>(
                setApiMode(this.<SearchResult<T>>createGet(url), apiMode),
                jsonParserTypeRef
        ).filter(filters);
    }

    @Override public <T> CommandRequest<T> createCommandRequest(
            String url, Command command, TypeReference<T> jsonParserTypeRef) {
        return new CommandRequestImpl<T>(this.<T>createPost(url), command, jsonParserTypeRef);
    }

    @Override public <T> CommandRequestWithErrorHandling<T> createCommandRequestWithErrorHandling(
            String url, Command command, int handledErrorStatus, TypeReference<T> jsonParserTypeRef) {
        return new CommandRequestWithErrorHandling<T>(this.<T>createPost(url), command, handledErrorStatus, jsonParserTypeRef);
    }

    // -----------------------------
    // API mode (staging / live)
    // -----------------------------

    private <T> RequestHolder<T> setApiMode(RequestHolder<T> requestHolder, ApiMode apiMode) {
        return requestHolder.addQueryParameter("staged", apiMode == ApiMode.Staging ? "true" : "false");
    }
}
