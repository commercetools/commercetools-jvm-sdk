package io.sphere.sdk.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;
import io.sphere.sdk.http.*;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.utils.JsonUtils;
import io.sphere.sdk.utils.SphereInternalLogger;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import static io.sphere.sdk.utils.SphereInternalLogger.*;

class HttpSphereRequestExecutor extends Base implements SphereRequestExecutor {
    private final ObjectMapper objectMapper = JsonUtils.newObjectMapper();
    private final HttpClient requestExecutor;
    private final String projectKey;

    public HttpSphereRequestExecutor(final HttpClient httpClient, final SphereApiConfig config) {
        this.requestExecutor = httpClient;
        this.projectKey = config.getProjectKey();
    }

    @Override
    public <T> CompletableFuture<T> execute(final SphereRequest<T> sphereRequest) {
        final SphereRequest<T> usedClientRequest = new CachedHttpRequestClientRequest<>(sphereRequest);
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

    private <T> Function<HttpResponse, T> preProcess(final SphereRequest<T> sphereRequest) {
        return new Function<HttpResponse, T>() {
            @Override
            public T apply(final HttpResponse httpResponse) {
                final SphereInternalLogger logger = getLogger(httpResponse);
                logger.debug(() -> httpResponse);
                logger.trace(() -> httpResponse.getStatusCode() + "\n" + httpResponse.getResponseBody().map(body -> JsonUtils.prettyPrintJsonStringSecure(bytesToString(body))).orElse("No body present.") + "\n");
                final T result;
                if (isErrorResponse(httpResponse) && !sphereRequest.canHandleResponse(httpResponse)) {
                    result = handleErrors(httpResponse, sphereRequest);
                } else {
                    try {
                        result = sphereRequest.resultMapper().apply(httpResponse);
                    } catch (final JsonException e) {
                        final byte[] bytes = httpResponse.getResponseBody().get();
                        throw new JsonParseException("Cannot parse " + bytesToString(bytes), e);
                    }
                }
                return result;
            }

        };
    }

    public <T> T handleErrors(final HttpResponse httpResponse, final SphereRequest<T> sphereRequest) {
        SphereErrorResponse errorResponse;
        try {
            if (!httpResponse.getResponseBody().isPresent()) {//the /model/id endpoint does not return JSON on 404
                errorResponse = SphereErrorResponse.of(httpResponse.getStatusCode(), "<no body>", Collections.<SphereError>emptyList());
            } else {
                errorResponse = objectMapper.readValue(httpResponse.getResponseBody().get(), SphereErrorResponse.typeReference());
            }
        } catch (final Exception e) {
            if (isServiceNotAvailable(httpResponse)) {
                throw new SphereServiceUnavailableException(e);
            } else {
                final SphereException exception = new SphereException("Can't parse backend response.", e);
                fillExceptionWithData(httpResponse, exception, sphereRequest);
                throw exception;
            }
        }
        final SphereBackendException exception;
        if (httpResponse.getStatusCode() == 409) {
            exception = new ConcurrentModificationException(sphereRequest.httpRequest().getPath(), errorResponse);
        } else if(!errorResponse.getErrors().isEmpty() && errorResponse.getErrors().get(0).getCode().equals("ReferenceExists")) {
            exception = new ReferenceExistsException(sphereRequest.httpRequest().getPath(), errorResponse);
        } else {
            exception = new SphereBackendException(sphereRequest.httpRequest().getPath(), errorResponse);
        }
        fillExceptionWithData(httpResponse, exception, sphereRequest);
        throw exception;
    }

    private boolean isServiceNotAvailable(final HttpResponse httpResponse) {
        return httpResponse.getStatusCode() == 503 || httpResponse.getResponseBody().map(b -> bytesToString(b)).map(s -> s.contains("<h2>Service Unavailable</h2>")).orElse(false);
    }

    private static String bytesToString(final byte[] b) {
        return new String(b, StandardCharsets.UTF_8);
    }

    private static boolean isErrorResponse(final HttpResponse httpResponse) {
        return httpResponse.getStatusCode() / 100 != 2;
    }

    private <T> void fillExceptionWithData(final HttpResponse httpResponse, final SphereException exception, final SphereRequest<T> sphereRequest) {
        exception.setSphereRequest(sphereRequest.toString());
        exception.setUnderlyingHttpRequest(sphereRequest.httpRequest());
        exception.setUnderlyingHttpResponse(httpResponse);
        exception.setProjectKey(projectKey);
    }
}