package io.sphere.internal.request;

import io.sphere.internal.Defaults;
import io.sphere.internal.util.Util;
import io.sphere.client.QueryRequest;

import com.google.common.util.concurrent.ListenableFuture;
import io.sphere.client.model.QueryResult;
import org.codehaus.jackson.type.TypeReference;

/** {@inheritDoc}  */
public class QueryRequestImpl<T> implements QueryRequest<T>, TestableRequest {
    RequestHolder<QueryResult<T>> requestHolder;
    TypeReference<QueryResult<T>> jsonParserTypeRef;
    private int pageSize = Defaults.pageSize;
    private int page = 0;

    public QueryRequestImpl(RequestHolder<QueryResult<T>> requestHolder, TypeReference<QueryResult<T>> jsonParserTypeRef) {
        this.requestHolder = requestHolder;
        this.jsonParserTypeRef = jsonParserTypeRef;
    }

    @Override public QueryRequest<T> page(int page) {
        // added to the query parameters in fetchAsync()
        this.page = page;
        return this;
    }

    @Override public QueryRequest<T> pageSize(int pageSize) {
        // added to the query parameters in fetchAsync()
        this.pageSize = pageSize;
        return this;
    }

    @Override public QueryResult<T> fetch() {
        return Util.sync(fetchAsync());
    }

    @Override public ListenableFuture<QueryResult<T>> fetchAsync() {
        requestHolder.addQueryParameter("limit", Integer.toString(this.pageSize));
        requestHolder.addQueryParameter("offset", Integer.toString(this.page * this.pageSize));
        return RequestExecutor.executeAndThrowOnError(requestHolder, jsonParserTypeRef);
    }

    @Override public QueryRequest<T> expand(String... paths) {
        for (String path: paths) {
            requestHolder.addQueryParameter("expand", path);
        }
        return this;
    }

    @Override public TestableRequestHolder getRequestHolder() {
        return requestHolder;
    }

    // logging and debugging purposes
    @Override public String toString() {
        return getRequestHolder().toString();
    }
}
