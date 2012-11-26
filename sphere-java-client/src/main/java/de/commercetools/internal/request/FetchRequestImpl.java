package de.commercetools.internal.request;

import com.google.common.base.Optional;
import de.commercetools.internal.Defaults;
import de.commercetools.sphere.client.FetchRequest;
import de.commercetools.sphere.client.SphereException;

import com.google.common.util.concurrent.ListenableFuture;
import org.codehaus.jackson.type.TypeReference;

/** {@inheritDoc}  */
public class FetchRequestImpl<T> implements FetchRequest<T> {
    RequestHolder<T> requestHolder;
    TypeReference<T> jsonParserTypeRef;

    public FetchRequestImpl(RequestHolder<T> requestHolder, TypeReference<T> jsonParserTypeRef) {
        this.requestHolder = requestHolder;
        this.jsonParserTypeRef = jsonParserTypeRef;
    }

    /** {@inheritDoc}  */
    public Optional<T> fetch() {
        try {
            return fetchAsync().get();
        } catch(Exception ex) {
            throw new SphereException(ex);
        }
    }

    /** {@inheritDoc}  */
    public ListenableFuture<Optional<T>> fetchAsync() {
        return RequestExecutor.executeAndHandleError(requestHolder, 404, jsonParserTypeRef);
    }

    /** {@inheritDoc}  */
    public FetchRequest<T> expand(String... paths) {
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
