package de.commercetools.sphere.client.util;

import de.commercetools.sphere.client.async.ListenableFutureAdapter;
import de.commercetools.sphere.client.BackendException;
import de.commercetools.sphere.client.util.Log;
import com.google.common.base.Charsets;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

/** @inheritdoc */
public class RequestBuilderImpl<T> implements RequestBuilder<T> {

    RequestHolder<T> requestHolder;
    TypeReference<T> jsonParserTypeRef;

    public RequestBuilderImpl(RequestHolder<T> requestHolder, TypeReference<T> jsonParserTypeRef) {
        this.requestHolder = requestHolder;
        this.jsonParserTypeRef = jsonParserTypeRef;
    }

    /** @inheritdoc */
    public T fetch() throws BackendException {
        try {
            return fetchAsync().get();
        } catch(Exception ex) {
            throw new BackendException(ex);
        }
    }

    /** @inheritdoc */
    public ListenableFuture<T> fetchAsync() throws BackendException {
        try {
            if (Log.isTraceEnabled()) {
                Log.trace(requestHolder.getRawUrl());
            }
            return new ListenableFutureAdapter<T>(requestHolder.executeRequest(new AsyncCompletionHandler<T>() {
                @Override
                public T onCompleted(Response response) throws Exception {
                    if (response.getStatusCode() != 200) {
                        String message = String.format(
                                "The backend returned an error response: %s\n[%s]\n%s",
                                requestHolder.getRawUrl(),
                                response.getStatusCode(),
                                response.getResponseBody(Charsets.UTF_8.name())
                        );
                        Log.error(message);
                        throw new BackendException(message);
                    } else {
                        ObjectMapper jsonParser = new ObjectMapper();
                        T parsed = jsonParser.readValue(response.getResponseBody(Charsets.UTF_8.name()), jsonParserTypeRef);
                        if (Log.isTraceEnabled()) {
                            // Log pretty printed json response
                            ObjectWriter writer = jsonParser.writerWithDefaultPrettyPrinter();
                            Log.trace("\n" + writer.writeValueAsString(parsed));
                        }
                        return parsed;
                    }
                }
            }));
        } catch (Exception e) {
            throw new BackendException(e);
        }
    }

    /** @inheritdoc */
    public RequestBuilder<T> expand(String... paths) {
        for (String path: paths) {
            requestHolder.addQueryParameter("expand", path);
        }
        return this;
    }
}
