package io.sphere.sdk.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;
import com.typesafe.config.Config;
import io.sphere.sdk.http.*;
import io.sphere.sdk.utils.JsonUtils;
import io.sphere.sdk.utils.SphereInternalLogger;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import static io.sphere.sdk.utils.SphereInternalLogger.*;

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
        final ClientRequest<T> usedClientRequest = new CachedHttpRequestClientRequest<>(clientRequest);
        final SphereInternalLogger logger = getLogger(usedClientRequest);
        logger.debug(() -> usedClientRequest);
        logger.trace(() -> {
            final String output;
            if (usedClientRequest.httpRequest() instanceof JsonBodyHttpRequest) {
                final String unformattedJson = ((JsonBodyHttpRequest) usedClientRequest.httpRequest()).getBody();
                output = "send: " + unformattedJson + "\nformatted: " + JsonUtils.prettyPrintJsonStringSecure(unformattedJson);
            } else {
                output = "no request body present";
            }
            return output;
        });
        return requestExecutor.
                execute(usedClientRequest).
                thenApply(preProcess(usedClientRequest));
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
                logger.trace(() -> httpResponse.getStatusCode() + "\n" + httpResponse.getResponseBody().map(body -> JsonUtils.prettyPrintJsonStringSecure(new String(body, StandardCharsets.UTF_8))).orElse("No body present.") + "\n");
                final T result;
                if (isErrorResponse(httpResponse) && !clientRequest.canHandleResponse(httpResponse)) {
                    result = handleErrors(httpResponse, clientRequest);
                } else {
                    result = clientRequest.resultMapper().apply(httpResponse);
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
            if (isServiceNotAvailable(httpResponse)) {
                throw new SphereServiceUnavailableException(e);
            } else {
                final SphereException exception = new SphereException("Can't parse backend response.", e);
                fillExceptionWithData(httpResponse, exception, clientRequest);
                throw exception;
            }
        }
        final SphereBackendException exception;
        if (httpResponse.getStatusCode() == 409) {
            exception = new ConcurrentModificationException(clientRequest.httpRequest().getPath(), errorResponse);
        } else if(!errorResponse.getErrors().isEmpty() && errorResponse.getErrors().get(0).getCode().equals("ReferenceExists")) {
            exception = new ReferenceExistsException(clientRequest.httpRequest().getPath(), errorResponse);
        } else {
            exception = new SphereBackendException(clientRequest.httpRequest().getPath(), errorResponse);
        }
        fillExceptionWithData(httpResponse, exception, clientRequest);
        throw exception;
    }

    private boolean isServiceNotAvailable(final HttpResponse httpResponse) {
        return httpResponse.getStatusCode() == 503 || httpResponse.getResponseBody().map(b -> new String(b, StandardCharsets.UTF_8)).map(s -> s.contains("<h2>Service Unavailable</h2>")).orElse(false);
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