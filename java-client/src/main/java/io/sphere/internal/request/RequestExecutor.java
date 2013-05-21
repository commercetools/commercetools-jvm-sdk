package io.sphere.internal.request;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;
import io.sphere.client.Result;
import io.sphere.internal.SphereErrorResponse;
import io.sphere.internal.util.Log;
import io.sphere.internal.util.Util;
import io.sphere.client.SphereBackendException;
import io.sphere.client.SphereException;
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
     *  Throws a SphereBackendException on any response with status other than 2xx.
     *
     *  <p>Use this method when sending requests to endpoints that are only expected to fail in conditions
     *  which shouldn't be handled by the application (such as programmer error, or 500 from Sphere),
     *  e.g. /product-projections/search. */
    public static <T> ListenableFuture<T> executeAndThrowOnError(
            final RequestHolder<T> requestHolder, final TypeReference<T> jsonParserTypeRef)
    {
        return Futures.transform(execute(requestHolder, jsonParserTypeRef), new Function<Result<T>, T>() {
            public T apply(@Nullable Result<T> result) {
                if (result.isError()) {
                    throw result.getError();
                }
                return result.getValue();
            }
        });
    }

    /** Executes request and parses JSON response.
     *
     *  Returns {@code Optional.absent} if the backend responds with given status code.
     *
     *  <p>Use this version when sending requests to endpoints where e.g. 404 Not Found is expected. */
    public static <T> ListenableFuture<Optional<T>> executeAndHandleError(
            final RequestHolder<T> requestHolder, final int handledErrorStatus, final TypeReference<T> jsonParserTypeRef)
    {
        return Futures.transform(execute(requestHolder, jsonParserTypeRef), new Function<Result<T>, Optional<T>>() {
            public Optional<T> apply(Result<T> result) {
                if (result.isError()) {
                    if (result.getError().getStatusCode() == handledErrorStatus) return Optional.absent();
                    throw result.getError();
                }
                return Optional.of(result.getValue());
            }
        });
    }

    /** Executes request and parses JSON response as given type. */
    private static <T> ListenableFuture<Result<T>> execute(final RequestHolder<T> requestHolder, final TypeReference<T> jsonParserTypeRef)
    {
        try {
            return requestHolder.executeRequest(new AsyncCompletionHandler<Result<T>>() {
                public Result<T> onCompleted(Response response) throws Exception {
                    int status = response.getStatusCode();
                    String body = response.getResponseBody(Charsets.UTF_8.name());
                    if (status / 100 != 2) {
                        SphereErrorResponse errorResponse = jsonParser.readValue(body, errorResponseJsonTypeRef);
                        Log.error(errorResponse + "\n\nRequest: " + requestHolderToString(requestHolder));
                        return Result.<T>error(new SphereBackendException(requestHolder.getUrl(), errorResponse));
                    } else {
                        if (Log.isTraceEnabled()) {
                            Log.trace(requestHolderToString(requestHolder) + "=> " +
                                    response.getStatusCode() + "\n" +
                                    Util.prettyPrintJsonStringSecure(body) + "\n");
                        } else if (Log.isDebugEnabled()) {
                            Log.debug(requestHolderToString(requestHolder));
                        }
                        return Result.<T>success(jsonParser.<T>readValue(body, jsonParserTypeRef));
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
                           "\n" + Util.prettyPrintJsonStringSecure(requestHolder.getBody()));
        } catch(IOException e) {
            throw new SphereException(e);
        }
    }
}
