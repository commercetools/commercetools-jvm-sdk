package io.sphere.sdk.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
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
            return requestBody.map(body ->
                    "send: " + body + " formatted: " + JsonUtils.prettyPrintJsonStringSecure(body)).orElse("no request body present");
        });
        return requestExecutor.
                execute(clientRequest).
                thenApply(preProcess(clientRequest));
    }

    @Override
    public void close() {
        requestExecutor.close();
    }

    private <T> Function<HttpResponse, T> preProcess(final ClientRequest<T> clientRequest) {
        return new Function<HttpResponse, T>() {
            @Override
            public T apply(final HttpResponse httpResponse) {
                final SphereInternalLogger logger = getLogger(httpResponse);
                logger.debug(() -> httpResponse);
                //TODO important
//                logger.trace(() -> httpResponse.getStatusCode() + "\n" + JsonUtils.prettyPrintJsonStringSecure(httpResponse.getResponseBodyAsString()) + "\n");

                final T result;
                if (isErrorResponse(httpResponse) && !clientRequest.canHandleResponse(httpResponse)){
                    result = handleErrors(httpResponse, clientRequest);
                } else {
                    result = clientRequest.resultMapper().apply(httpResponse);
                }

                //TODO refactor
                if (!httpResponse.getResponseBody().isPresent()) {
                    try {
                        httpResponse.getResponseBody().get().close();
                    } catch (IOException e) {
                        throw new RuntimeException();
                    }
                }
                return result;
            }
        };
    }

    public <T> T handleErrors(final HttpResponse httpResponse, final ClientRequest<T> clientRequest) {
        SphereErrorResponse errorResponse;
        try {
            if (!httpResponse.getResponseBody().isPresent()) {//the /model/id endpoint does not return JSON on 404
                errorResponse = new SphereErrorResponse(httpResponse.getStatusCode(), "<no body>", Collections.<SphereError>emptyList());
            } else {
                errorResponse = objectMapper.readValue(httpResponse.getResponseBody().get(), SphereErrorResponse.typeReference());
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
    }

    private static boolean isErrorResponse(final HttpResponse httpResponse) {
        return httpResponse.getStatusCode() / 100 != 2;
    }

    private <T> void fillExceptionWithData(final HttpResponse httpResponse, final SphereException exception, final ClientRequest<T> clientRequest) {
        exception.setSphereRequest(clientRequest.toString());
        exception.setUnderlyingHttpRequest(clientRequest.httpRequest());
        exception.setUnderlyingHttpResponse(httpResponse);
        exception.setProjectKey(config.getString("sphere.project"));
    }
}