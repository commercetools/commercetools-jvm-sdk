package io.sphere.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.typesafe.config.Config;
import io.sphere.client.exceptions.SphereBackendException;
import io.sphere.client.exceptions.SphereException;
import io.sphere.internal.errors.SphereErrorResponse;
import io.sphere.internal.util.Log;
import io.sphere.internal.util.Util;
import io.sphere.internal.util.json.SphereObjectMapperFactory;

public class HttpSphereRequestExecutor implements SphereRequestExecutor {
    private static final TypeReference<SphereErrorResponse> errorResponseJsonTypeRef = new TypeReference<SphereErrorResponse>() {
    };
    private final ObjectMapper jsonParser = SphereObjectMapperFactory.newObjectMapper();
    private final HttpClient requestExecutor;

    public HttpSphereRequestExecutor(final HttpClient requestExecutor, final Config config) {
        this.requestExecutor = requestExecutor;
    }

    @Override
    public <T> ListenableFuture<Optional<T>> execute(final Fetch<T> fetch) {
        final ListenableFuture<HttpResponse> future = requestExecutor.execute(fetch);
        return Futures.transform(future, new Function<HttpResponse, Optional<T>>() {
            @Override
            public Optional<T> apply(final HttpResponse httpResponse) {
                final SphereResultRaw<T> sphereResultRaw = requestToSphereResult(httpResponse, fetch);
                Optional<T> result = Optional.absent();
                if (sphereResultRaw.isSuccess()) {
                    result = Optional.of(sphereResultRaw.getValue());
                }
                return result;
            }
        });
    }

    private <T> SphereResultRaw<T> requestToSphereResult(final HttpResponse httpResponse, final Requestable<T> requestable) {
        final int status = httpResponse.getStatusCode();
        final String body = httpResponse.getResponseBody();
        final boolean hasError = status / 100 != 2;
        if (hasError) {
            SphereErrorResponse errorResponse = null;
            try {
                errorResponse = jsonParser.readValue(body, errorResponseJsonTypeRef);
            } catch (Exception e) {
                // This can only happen when the backend and SDK don't match.
                Log.error(
                        "Can't parse backend response: \n[" + status + "]\n" + body + "\n\nRequest: " + requestable);
                throw new SphereException("Can't parse backend response.", e);
            }
            if ((status >= 400 && status < 500) && Log.isDebugEnabled()) {
                Log.debug(errorResponse + "\n\nRequest: " + requestable);
            } else if (status >= 500) {
                Log.error(errorResponse + "\n\nRequest: " + requestable);
            }
            return SphereResultRaw.<T>error(new SphereBackendException(requestable.httpRequest().getUrl(), errorResponse));
        } else {
            try {
                if (Log.isTraceEnabled()) {
                    Log.trace(requestable + "\n=> " + httpResponse.getStatusCode() + "\n" + Util.prettyPrintJsonStringSecure(body) + "\n");
                } else if (Log.isDebugEnabled()) {
                    Log.debug(requestable.toString());
                }
                return SphereResultRaw.<T>success(jsonParser.<T>readValue(body, requestable.typeReference()));
            } catch (final Exception e) {
                throw Util.toSphereException(e);
            }
        }
    }
}