package io.sphere.sdk.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Function;
import io.sphere.sdk.http.*;
import io.sphere.sdk.meta.BuildInfo;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.utils.JsonUtils;
import io.sphere.sdk.utils.SphereInternalLogger;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import static io.sphere.sdk.utils.SphereInternalLogger.*;

class HttpSphereRequestExecutor extends Base implements SphereRequestExecutor {
    private final ObjectMapper objectMapper = JsonUtils.newObjectMapper();
    private final HttpClient httpClient;
    private final SphereApiConfig config;
    private final SphereAccessTokenSupplier tokenSupplier;

    public HttpSphereRequestExecutor(final HttpClient httpClient, final SphereApiConfig config, final SphereAccessTokenSupplier tokenSupplier) {
        this.httpClient = httpClient;
        this.config = config;
        this.tokenSupplier = tokenSupplier;
    }

    @Override
    public <T> CompletableFuture<T> execute(final SphereRequest<T> sphereRequest) {
        final SphereRequest<T> usedClientRequest = new CachedHttpRequestClientRequest<>(sphereRequest);
        final HttpRequest httpRequest = createHttpRequest(sphereRequest, tokenSupplier.get());
        final SphereInternalLogger logger = getLogger(httpRequest);
        logger.debug(() -> usedClientRequest);
        logger.trace(() -> {
            final String output;
            final Optional<HttpRequestBody> bodyOption = usedClientRequest.httpRequestIntent().getBody();
            if (bodyOption.isPresent() && bodyOption.get() instanceof StringHttpRequestBody) {
                final String unformattedJson = ((StringHttpRequestBody) bodyOption.get()).getUnderlying();
                output = "send: " + unformattedJson + "\nformatted: " + JsonUtils.prettyPrintJsonStringSecure(unformattedJson);
            } else {
                output = "no request body present";
            }
            return output;
        });

        return httpClient.
                execute(httpRequest).
                thenApply(preProcess(usedClientRequest));
    }

    private <T> HttpRequest createHttpRequest(final SphereRequest<T> sphereRequest, final String token) {
        return sphereRequest
                .httpRequestIntent()
                .plusHeader("User-Agent", "SPHERE.IO JVM SDK " + BuildInfo.version())
                .plusHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .prefixPath("/" + config.getProjectKey())
                .toHttpRequest(config.getApiUrl());
    }

    @Override
    public void close() {
        httpClient.close();
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
            exception = new ConcurrentModificationException(sphereRequest.httpRequestIntent().getPath(), errorResponse);
        } else if(!errorResponse.getErrors().isEmpty() && errorResponse.getErrors().get(0).getCode().equals("ReferenceExists")) {
            exception = new ReferenceExistsException(sphereRequest.httpRequestIntent().getPath(), errorResponse);
        } else {
            exception = new SphereBackendException(sphereRequest.httpRequestIntent().getPath(), errorResponse);
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
        exception.setUnderlyingHttpRequest(sphereRequest.httpRequestIntent());
        exception.setUnderlyingHttpResponse(httpResponse);
        exception.setProjectKey(config.getProjectKey());
    }
}