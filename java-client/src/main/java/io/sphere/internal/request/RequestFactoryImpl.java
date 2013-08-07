package io.sphere.internal.request;

import com.google.common.base.Optional;
import io.sphere.client.*;
import io.sphere.internal.command.Command;
import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.model.QueryResult;
import io.sphere.client.model.SearchResult;
import io.sphere.client.shop.ApiMode;
import net.jcip.annotations.Immutable;
import org.codehaus.jackson.type.TypeReference;

import java.util.Locale;

/** Creates specialized requests based on basic GET and POST requests. */
@Immutable
public class RequestFactoryImpl implements RequestFactory {

    private final BasicRequestFactory basic;
    public RequestFactoryImpl(BasicRequestFactory basic) {
        this.basic = basic;
    }

    // -----------------
    // Read
    // -----------------

    @Override public <T> FetchRequest<T> createFetchRequest(
            String url, Optional<ApiMode> apiMode, TypeReference<T> jsonParserTypeRef) {
        return new FetchRequestImpl<T>(withApiMode(basic.<T>createGet(url), apiMode), jsonParserTypeRef);
    }

    @Override public <T> FetchRequest<T> createFetchRequestWithErrorHandling(
            String url, Optional<ApiMode> apiMode, int handledErrorStatus, TypeReference<T> jsonParserTypeRef) {
        return new FetchRequestWithErrorHandling<T>(withApiMode(basic.<T>createGet(url), apiMode), handledErrorStatus, jsonParserTypeRef);
    }

    @Override public <T> FetchRequest<T> createFetchRequestBasedOnQuery(
            String url, Optional<ApiMode> apiMode, TypeReference<QueryResult<T>> jsonParserTypeRef) {
        return new FetchRequestBasedOnQuery<T>(createQueryRequest(url, apiMode, jsonParserTypeRef));
    }

    @Override public <T> QueryRequest<T> createQueryRequest(
            String url, Optional<ApiMode> apiMode, TypeReference<QueryResult<T>> jsonParserTypeRef) {
        return new QueryRequestImpl<T>(withApiMode(basic.<QueryResult<T>>createGet(url), apiMode), jsonParserTypeRef);
    }

    @Override public <T> SearchRequest<T> createSearchRequest(
            String url, Optional<ApiMode> apiMode, Iterable<FilterExpression> filters, TypeReference<SearchResult<T>> jsonParserTypeRef, Locale locale) {
        return new SearchRequestImpl<T>(withApiMode(basic.<SearchResult<T>>createGet(url), apiMode), jsonParserTypeRef, locale).filter(filters);
    }

    // -----------------
    // Write
    // -----------------

    @Override public <T> CommandRequest<T> createCommandRequest(
            String url, Command command, TypeReference<T> jsonParserTypeRef) {
        return new CommandRequestImpl<T>(basic.<T>createPost(url), command, jsonParserTypeRef);
    }

    // -----------------
    // Delete
    // -----------------

    @Override
    public <T> DeleteRequest<T> createDeleteRequest(String url, TypeReference<T> jsonParserTypeRef) {
        return new DeleteRequestImpl<T>(basic.<T>createDelete(url), jsonParserTypeRef);
    }

    // -----------------------------------
    // API mode helper (staging / live)
    // -----------------------------------

    private <T> RequestHolder<T> withApiMode(RequestHolder<T> requestHolder, Optional<ApiMode> apiMode) {
        return apiMode.isPresent() ?
                requestHolder.addQueryParameter("staged", apiMode.get() == ApiMode.Staged ? "true" : "false") :
                requestHolder;
    }
}