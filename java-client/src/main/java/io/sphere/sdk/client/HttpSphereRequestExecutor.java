package io.sphere.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.function.Function;
import com.google.common.base.Strings;
import com.typesafe.config.Config;
import io.sphere.sdk.requests.ClientRequest;
import io.sphere.sdk.requests.HttpResponse;
import io.sphere.sdk.utils.JsonUtils;
import io.sphere.sdk.utils.Log;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class HttpSphereRequestExecutor implements SphereRequestExecutor {
    private static final TypeReference<SphereErrorResponse> errorResponseJsonTypeRef = new TypeReference<SphereErrorResponse>() {
    };
    private final ObjectMapper objectMapper = JsonUtils.newObjectMapper();
    private final HttpClient requestExecutor;
    private final Config config;

    public HttpSphereRequestExecutor(final HttpClient httpClient, final Config config) {
        this.requestExecutor = httpClient;
        this.config = config;
    }

    @Override
    public <T> CompletableFuture<T> execute(final ClientRequest<T> clientRequest) {
        final CompletableFuture<HttpResponse> future = requestExecutor.execute(clientRequest);
        final Function<HttpResponse, T> underlying = clientRequest.resultMapper();
        return future.thenApply(preProcess(clientRequest, underlying));
    }

    @Override
    public void close() {
        requestExecutor.close();
    }

    private <T> Function<HttpResponse, T> preProcess(final ClientRequest<T> clientRequest,
                                                     final Function<HttpResponse, T> underlying) {
        return new Function<HttpResponse, T>() {
            @Override
            public T apply(final HttpResponse httpResponse) {
                final int status = httpResponse.getStatusCode();
                final String body = httpResponse.getResponseBody();
                final boolean hasError = status / 100 != 2;
                if (hasError) {
                    SphereErrorResponse errorResponse;
                    try {
                        if (Strings.isNullOrEmpty(body)) {//the /model/id endpoint does not return JSON on 404
                            errorResponse = new SphereErrorResponse(status, "<no body>", Collections.<SphereError>emptyList());
                        } else {
                            errorResponse = objectMapper.readValue(body, errorResponseJsonTypeRef);
                        }
                    } catch (final Exception e) {
                        // This can only happen when the backend and SDK don't match.

                        final SphereException exception = new SphereException("Can't parse backend response", e);
                        fillExceptionWithData(httpResponse, exception, clientRequest);
                        throw exception;
                    }
                    if ((status >= 400 && status < 500) && Log.isDebugEnabled()) {
                        Log.debug(errorResponse + "\n\nRequest: " + clientRequest);
                    } else if (status >= 500) {
                        Log.error(errorResponse + "\n\nRequest: " + clientRequest);
                    }
                    final SphereBackendException exception = new SphereBackendException(clientRequest.httpRequest().getPath(), errorResponse);
                    fillExceptionWithData(httpResponse, exception, clientRequest);
                    throw exception;
                } else {
                    if (Log.isTraceEnabled()) {
                        Log.trace(clientRequest + "\n=> " + httpResponse.getStatusCode() + "\n" + JsonUtils.prettyPrintJsonStringSecure(httpResponse.getResponseBody()) + "\n");
                    } else if (Log.isDebugEnabled()) {
                        Log.debug(clientRequest.toString());
                    }
                    return underlying.apply(httpResponse);
                }
            }
        };
    }

    private <T> void fillExceptionWithData(final HttpResponse httpResponse, final SphereException exception, final ClientRequest<T> clientRequest) {
        exception.setSphereRequest(clientRequest.toString());
        exception.setUnderlyingHttpRequest(clientRequest.httpRequest());
        exception.setUnderlyingHttpResponse(httpResponse);
        exception.setProjectKey(config.getString("sphere.project"));
    }
}