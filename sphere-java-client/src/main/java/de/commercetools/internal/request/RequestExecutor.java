package de.commercetools.internal.request;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;
import de.commercetools.internal.util.Log;
import de.commercetools.internal.util.Util;
import de.commercetools.sphere.client.ConflictException;
import de.commercetools.sphere.client.SphereBackendException;
import de.commercetools.sphere.client.SphereException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.annotation.Nullable;
import java.io.IOException;

public class RequestExecutor {
    /** Executes request and parses JSON response as given type.
     *  Throws an Exception on 404 Not Found.
     *  Use this version when sending requests to endpoints that should never return 404 (such as /product-projections/search). */
    public static <T> ListenableFuture<T> executeAndThrowOnError(final RequestHolder<T> requestHolder, final TypeReference<T> jsonParserTypeRef) {
        return execute(requestHolder, null, jsonParserTypeRef);
    }

    /** Executes request and parses JSON response as given type.
     *  Returns {@link com.google.common.base.Optional#absent()} if the backend responds with 404 Not Found.
     *  Use this version when sending requests to endpoints where 404 is common (such as /product-projections/id). */
    public static <T> ListenableFuture<Optional<T>> executeAndHandleError(final RequestHolder<T> requestHolder, int handledErrorStatus, final TypeReference<T> jsonParserTypeRef) {
        return Futures.transform(execute(requestHolder, handledErrorStatus, jsonParserTypeRef), new Function<T, Optional<T>>() {
            public Optional<T> apply(@Nullable T res) {
                return Optional.fromNullable(res);
            }
        });
    }

    /** Executes request and parses JSON response as given type. */
    private static <T> ListenableFuture<T> execute(final RequestHolder<T> requestHolder, final Integer handleErrorStatus, final TypeReference<T> jsonParserTypeRef) {
        try {
            return requestHolder.executeRequest(new AsyncCompletionHandler<T>() {
                public T onCompleted(Response response) throws Exception {
                    String body = response.getResponseBody(Charsets.UTF_8.name());
                    int status = response.getStatusCode();
                    if (handleErrorStatus != null && status == handleErrorStatus) {
                        Log.warn(requestHolderToString(requestHolder));
                        return null;
                    }
                    if (status / 100 != 2) {
                        Exception e;
                        switch (status) {
                            case 409: e = new ConflictException(requestHolder.getRawUrl(), body); break;
                            case 404: // Intentional fall through.
                            default: e = new SphereBackendException(status, requestHolder.getRawUrl(), body); break;
                        }
                        Log.error(e.getMessage() + "\n\nRequest: " + requestHolderToString(requestHolder));
                        throw e;
                    } else {
                        if (Log.isTraceEnabled()) {
                            Log.trace(requestHolderToString(requestHolder) + "=> " + response.getStatusCode());
                        }
                        ObjectMapper jsonParser = new ObjectMapper();
                        return jsonParser.readValue(response.getResponseBody(Charsets.UTF_8.name()), jsonParserTypeRef);
                    }
                }
            });
        } catch (Exception e) {
            throw new SphereException(e);
        }
    }

    private static <T> String requestHolderToString(RequestHolder<T> requestHolder) {
        try {
            return requestHolder.getMethod() + " " +
                   requestHolder.getRawUrl() +
                   (Strings.isNullOrEmpty(requestHolder.getBody()) ?
                           "" :
                           "\n" + Util.prettyPrintJsonString(requestHolder.getBody())) +
                   "\n";
        } catch(IOException e) {
            throw new SphereException(e);
        }
    }

    private static String jsonResponseToString(Response response) {
        try {
            return Util.prettyPrintJsonString(response.getResponseBody(Charsets.UTF_8.name()));
        } catch(IOException e) {
            throw new SphereException(e);
        }
    }
}
