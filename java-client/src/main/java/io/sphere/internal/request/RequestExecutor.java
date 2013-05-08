package io.sphere.internal.request;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;
import io.sphere.internal.ChaosMode;
import io.sphere.internal.util.Log;
import io.sphere.internal.util.Util;
import io.sphere.client.ConflictException;
import io.sphere.client.SphereBackendException;
import io.sphere.client.SphereException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.annotation.Nullable;
import java.io.IOException;

public class RequestExecutor {
    private static final ObjectMapper jsonParser = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /** Executes request and parses JSON response as given type.
     *  Throws an Exception on 404 Not Found.
     *  Use this version when sending requests to endpoints that should never return 404 (such as /product-projections/search). */
    public static <T> ListenableFuture<T> executeAndThrowOnError(
            final RequestHolder<T> requestHolder, final TypeReference<T> jsonParserTypeRef)
    {
        return execute(requestHolder, null, jsonParserTypeRef);
    }

    /** Executes request and parses JSON response as given type.
     *  Returns {@link com.google.common.base.Optional#absent()} if the backend responds with 404 Not Found.
     *  Use this version when sending requests to endpoints where 404 is common (such as /product-projections/id). */
    public static <T> ListenableFuture<Optional<T>> executeAndHandleError(
            final RequestHolder<T> requestHolder, int handledErrorStatus, final TypeReference<T> jsonParserTypeRef)
    {
        return Futures.transform(execute(requestHolder, handledErrorStatus, jsonParserTypeRef), new Function<T, Optional<T>>() {
            public Optional<T> apply(@Nullable T res) {
                return Optional.fromNullable(res);
            }
        });
    }

    /** Executes request and parses JSON response as given type. */
    private static <T> ListenableFuture<T> execute(
            final RequestHolder<T> requestHolder, final Integer handleErrorStatus, final TypeReference<T> jsonParserTypeRef)
    {
        try {
            return requestHolder.executeRequest(new AsyncCompletionHandler<T>() {
                public T onCompleted(Response response) throws Exception {
                    if (ChaosMode.isUnexpectedChaos()) {
                        throw new Exception("Chaos: Simulating unexpected exception.");
                    }
                    int status = response.getStatusCode();
                    String body = response.getResponseBody(Charsets.UTF_8.name());
                    if (ChaosMode.isChaos()) {
                        ChaosMode.HttpResponse chaosResponse = ChaosMode.chaosHttpResponse();
                        status = chaosResponse.getStatus();
                        body = chaosResponse.getBody();
                    }
                    if (handleErrorStatus != null && status == handleErrorStatus) {
                        Log.warn(requestHolderToString(requestHolder));
                        return null;
                    }
                    if (status / 100 != 2) {
                        Exception e;
                        switch (status) {
                            case 409: e = new ConflictException(requestHolder.getUrl(), body); break;
                            case 404: // Intentional fall through.
                            default: e = new SphereBackendException(status, requestHolder.getUrl(), body); break;
                        }
                        Log.error(e.getMessage() + "\n\nRequest: " + requestHolderToString(requestHolder));
                        throw e;
                    } else {
                        if (Log.isTraceEnabled()) {
                            Log.trace(requestHolderToString(requestHolder) + "=> " +
                                    response.getStatusCode() + "\n" +
                                    Util.prettyPrintJsonStringSecure(response.getResponseBody(Charsets.UTF_8.name())));
                        }
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
                   requestHolder.getUrl() +
                   (Strings.isNullOrEmpty(requestHolder.getBody()) ?
                           "" :
                           "\n" + Util.prettyPrintJsonStringSecure(requestHolder.getBody())) +
                   "\n";
        } catch(IOException e) {
            throw new SphereException(e);
        }
    }
}
