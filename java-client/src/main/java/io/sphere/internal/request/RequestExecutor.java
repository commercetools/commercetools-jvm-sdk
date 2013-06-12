package io.sphere.internal.request;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;
import io.sphere.client.exceptions.SphereException;
import io.sphere.internal.errors.SphereErrorResponse;
import io.sphere.internal.util.Log;
import io.sphere.internal.util.Util;
import io.sphere.client.exceptions.SphereBackendException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.annotation.Nullable;
import java.io.IOException;

public class RequestExecutor {
    private static final ObjectMapper jsonParser = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final TypeReference<SphereErrorResponse> errorResponseJsonTypeRef = new TypeReference<SphereErrorResponse>() {};

    /** Executes request and parses JSON response.
     *
     *  Throws a {@link SphereBackendException} on any response with status other than 2xx.
     *
     *  <p>Use this method when sending requests to endpoints that are only expected to fail in conditions
     *  which shouldn't be handled by the application (such as programmer error, or 500 from Sphere),
     *  e.g. /product-projections/search. */
    public static <T> ListenableFuture<T> executeAndThrowOnError(
            final RequestHolder<T> requestHolder, final TypeReference<T> jsonParserTypeRef)
    {
        return Futures.transform(execute(requestHolder, jsonParserTypeRef), new Function<SphereResultRaw<T>, T>() {
            public T apply(@Nullable SphereResultRaw<T> result) {
                if (result.isError()) {
                    throw Util.toSphereException(result.getError());
                }
                return result.getValue();
            }
        });
    }

    /** Support for FetchRequest. Executes request and parses JSON response.
     *
     *  Returns {@code Optional.absent} if the backend responds with given status code.
     *
     *  <p>Use this version when sending requests to endpoints where e.g. 404 Not Found is expected. */
    public static <T> ListenableFuture<Optional<T>> executeAndHandleError(
            final RequestHolder<T> requestHolder, final int handledErrorStatus, final TypeReference<T> jsonParserTypeRef)
    {
        return Futures.transform(execute(requestHolder, jsonParserTypeRef), new Function<SphereResultRaw<T>, Optional<T>>() {
            public Optional<T> apply(SphereResultRaw<T> result) {
                if (result.isError()) {
                    if (result.getError().getStatusCode() == handledErrorStatus) {
                        return Optional.absent();
                    } else {
                        // An unexpected error response that shouldn't happen with FetchRequests. Fail the future.
                        throw Util.toSphereException(result.getError());
                    }
                }
                return Optional.of(result.getValue());
            }
        });
    }

    /** Executes request and parses JSON response as given type. */
    public static <T> ListenableFuture<SphereResultRaw<T>> execute(final RequestHolder<T> requestHolder, final TypeReference<T> jsonParserTypeRef)
    {
        try {
            return requestHolder.executeRequest(new AsyncCompletionHandler<SphereResultRaw<T>>() {
                public SphereResultRaw<T> onCompleted(Response response) throws Exception {
                    int status = response.getStatusCode();
                    String body = response.getResponseBody(Charsets.UTF_8.name());
                    if (status / 100 != 2) {
                        SphereErrorResponse errorResponse = null;
                        try {
                            errorResponse = jsonParser.readValue(body, errorResponseJsonTypeRef);
                        } catch (Exception e) {
                            // This can only happen when the backend and SDK don't match.
                            Log.error(
                                    "Can't parse backend response: \n[" + status + "]\n" + body + "\n\nRequest: " + requestHolderToString(requestHolder));
                            throw new SphereException("Can't parse backend response.", e);
                        }
                        if (Log.isErrorEnabled()) {
                            Log.error(errorResponse + "\n\nRequest: " + requestHolderToString(requestHolder));
                        }
                        return SphereResultRaw.<T>error(new SphereBackendException(requestHolder.getUrl(), errorResponse));
                    } else {
                        if (Log.isTraceEnabled()) {
                            Log.trace(requestHolderToString(requestHolder) + "\n" +
                                    "=> " + response.getStatusCode() + "\n" +
                                    Util.prettyPrintJsonStringSecure(body) + "\n");
                        } else if (Log.isDebugEnabled()) {
                            Log.debug(requestHolderToString(requestHolder));
                        }
                        return SphereResultRaw.<T>success(jsonParser.<T>readValue(body, jsonParserTypeRef));
                    }
                }
            });
        } catch (Exception e) {
            throw Util.toSphereException(e);
        }
    }

    private static <T> String requestHolderToString(RequestHolder<T> requestHolder) {
        try {
            return requestHolder.getMethod() + " " +
                   requestHolder.getUrl() +
                   (Strings.isNullOrEmpty(requestHolder.getBody()) ?
                           "" :
                           "\n" + Util.prettyPrintJsonStringSecure(requestHolder.getBody()));
        } catch(IOException e) {
            throw Util.toSphereException(e);
        }
    }
}
