package de.commercetools.internal.request;

import de.commercetools.internal.Defaults;
import de.commercetools.sphere.client.SphereException;
import de.commercetools.sphere.client.QueryRequest;

import com.google.common.util.concurrent.ListenableFuture;
import org.codehaus.jackson.type.TypeReference;

/** {@inheritDoc}  */
public class QueryRequestImpl<T> implements QueryRequest<T> {
    RequestHolder<T> requestHolder;
    TypeReference<T> jsonParserTypeRef;
    private int pageSize = Defaults.pageSize;
    private int page = 0;

    public QueryRequestImpl(RequestHolder<T> requestHolder, TypeReference<T> jsonParserTypeRef) {
        this.requestHolder = requestHolder;
        this.jsonParserTypeRef = jsonParserTypeRef;
    }

    public QueryRequest<T> page(int page) {
        // added to the query parameters in fetchAsync()
        this.page = page;
        return this;
    }

    public QueryRequest<T> pageSize(int pageSize) {
        // added to the query parameters in fetchAsync()
        this.pageSize = pageSize;
        return this;
    }

    /** {@inheritDoc}  */
    public T fetch() {
        try {
            return fetchAsync().get();
        } catch(Exception ex) {
            throw new SphereException(ex);
        }
    }

    /** {@inheritDoc}  */
    public ListenableFuture<T> fetchAsync() {
        requestHolder.addQueryParameter("limit", Integer.toString(this.pageSize));
        requestHolder.addQueryParameter("offset", Integer.toString(this.page * this.pageSize));
        return RequestExecutor.execute(requestHolder, jsonParserTypeRef);
    }

    /** {@inheritDoc}  */
    public QueryRequest<T> expand(String... paths) {
        for (String path: paths) {
            requestHolder.addQueryParameter("expand", path);
        }
        return this;
    }

    /** The URL the request will be sent to, for debugging purposes. */
    public String getRawUrl() {
        return this.requestHolder.getRawUrl();
    }
}
