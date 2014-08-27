package io.sphere.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;
import java.util.function.Function;
import com.typesafe.config.Config;
import io.sphere.sdk.http.ClientRequest;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.utils.JsonUtils;
import io.sphere.sdk.utils.SphereInternalLogger;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import static io.sphere.sdk.utils.SphereInternalLogger.*;
import static org.apache.commons.lang3.StringUtils.isEmpty;

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
        final SphereInternalLogger logger = getLogger(clientRequest);
        logger.debug(() -> clientRequest);
        logger.trace(() -> {
            final Optional<String> requestBody = clientRequest.httpRequest().getBody();
            return requestBody.map(body -> JsonUtils.prettyPrintJsonStringSecure(body)).orElse("no request body present");
        });
        return requestExecutor.
                execute(clientRequest).
                thenApply(preProcess(clientRequest, clientRequest.resultMapper()));
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
                final SphereInternalLogger logger = getLogger(httpResponse);
                logger.debug(() -> httpResponse.withoutRequest());
                logger.trace(() -> httpResponse.getStatusCode() + "\n" + JsonUtils.prettyPrintJsonStringSecure(httpResponse.getResponseBody()) + "\n");
                final int status = httpResponse.getStatusCode();
                final String body = httpResponse.getResponseBody();
                final boolean hasError = status / 100 != 2;
                if (hasError) {
                    SphereErrorResponse errorResponse;
                    try {
                        if (isEmpty(body)) {//the /model/id endpoint does not return JSON on 404
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
                    final SphereBackendException exception = new SphereBackendException(clientRequest.httpRequest().getPath(), errorResponse);
                    fillExceptionWithData(httpResponse, exception, clientRequest);
                    throw exception;
                } else {
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