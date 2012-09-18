package de.commercetools.internal;

import de.commercetools.sphere.client.async.ListenableFutureAdapter;
import de.commercetools.sphere.client.BackendException;
import de.commercetools.sphere.client.util.Log;
import de.commercetools.sphere.client.util.Util;
import de.commercetools.sphere.client.util.RequestBuilder;

import com.google.common.base.Charsets;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

/** {@inheritDoc}  */
public class RequestBuilderImpl<T> implements RequestBuilder<T> {
    RequestHolder<T> requestHolder;
    TypeReference<T> jsonParserTypeRef;

    public RequestBuilderImpl(RequestHolder<T> requestHolder, TypeReference<T> jsonParserTypeRef) {
        this.requestHolder = requestHolder;
        this.jsonParserTypeRef = jsonParserTypeRef;
    }

    /** {@inheritDoc}  */
    public T fetch() {
        try {
            return fetchAsync().get();
        } catch(Exception ex) {
            throw new BackendException(ex);
        }
    }

    /** {@inheritDoc}  */
    public ListenableFuture<T> fetchAsync() {
        return RequestHolders.execute(requestHolder, jsonParserTypeRef);
    }

    /** {@inheritDoc}  */
    public RequestBuilder<T> expand(String... paths) {
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
