package de.commercetools.internal.request;

import de.commercetools.internal.command.Command;
import de.commercetools.sphere.client.FetchRequest;
import de.commercetools.sphere.client.filters.expressions.FilterExpression;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.SearchRequest;
import de.commercetools.sphere.client.model.SearchResult;
import com.ning.http.client.AsyncHttpClient;
import net.jcip.annotations.Immutable;
import org.codehaus.jackson.type.TypeReference;
import de.commercetools.sphere.client.CommandRequest;

/** Creates requests that do real HTTP request. Can be mocked in tests. */
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

    @Override public <T> FetchRequest<T> createFetchRequest(String url, TypeReference<T> jsonParserTypeRef) {
        return new FetchRequestImpl<T>(this.<T>createGet(url), jsonParserTypeRef);
    }

    @Override public <T> FetchRequest<T> createFetchRequestWithErrorHandling(String url, int handledErrorStatus, TypeReference<T> jsonParserTypeRef) {
        return new FetchRequestWithErrorHandling<T>(this.<T>createGet(url), handledErrorStatus, jsonParserTypeRef);
    }

    @Override public <T> FetchRequest<T> createFetchRequestBasedOnQuery(String url, TypeReference<QueryResult<T>> jsonParserTypeRef) {
        return new FetchRequestBasedOnQuery<T>(createQueryRequest(url, jsonParserTypeRef));
    }

    @Override public <T> QueryRequest<T> createQueryRequest(String url, TypeReference<QueryResult<T>> jsonParserTypeRef) {
        return new QueryRequestImpl<T>(this.<QueryResult<T>>createGet(url), jsonParserTypeRef);
    }

    @Override public <T> SearchRequest<T> createSearchRequest(
            String url, Iterable<FilterExpression> filters, TypeReference<SearchResult<T>> jsonParserTypeRef) {
        return new SearchRequestImpl<T>(this.<SearchResult<T>>createGet(url), jsonParserTypeRef).filtered(filters);
    }

    @Override public <T> CommandRequest<T> createCommandRequest(String url, Command command, TypeReference<T> jsonParserTypeRef) {
        return new CommandRequestImpl<T>(this.<T>createPost(url), command, jsonParserTypeRef);
    }

    @Override public <T> CommandRequestWithErrorHandling<T> createCommandRequestWithErrorHandling(String url, Command command, int handledErrorStatus, TypeReference<T> jsonParserTypeRef) {
        return new CommandRequestWithErrorHandling<T>(this.<T>createPost(url), command, handledErrorStatus, jsonParserTypeRef);
    }
}
