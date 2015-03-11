package io.sphere.sdk.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.http.*;
import io.sphere.sdk.meta.BuildInfo;
import io.sphere.sdk.models.SphereException;
import io.sphere.sdk.json.JsonUtils;
import io.sphere.sdk.utils.SphereInternalLogger;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static io.sphere.sdk.client.HttpResponseBodyUtils.bytesToString;
import static io.sphere.sdk.utils.SphereInternalLogger.getLogger;

final class SphereClientImpl extends AutoCloseableService implements SphereClient {
    private final ObjectMapper objectMapper = JsonUtils.newObjectMapper();
    private final HttpClient httpClient;
    private final SphereApiConfig config;
    private final SphereAccessTokenSupplier tokenSupplier;


    private SphereClientImpl(final SphereApiConfig config, final SphereAccessTokenSupplier tokenSupplier, final HttpClient httpClient) {
        this.httpClient = httpClient;
        this.config = config;
        this.tokenSupplier = tokenSupplier;
    }

    @Override
    public <T> CompletableFuture<T> execute(final SphereRequest<T> sphereRequest) {
        final CompletableFuture<String> tokenFuture = tokenSupplier.get();
        return tokenFuture.thenCompose(token -> execute(sphereRequest, token));
    }

    private <T> CompletableFuture<T> execute(final SphereRequest<T> sphereRequest, final String token) {
        final HttpRequest httpRequest = createHttpRequest(sphereRequest, token);

        final SphereInternalLogger logger = getLogger(httpRequest);
        logger.debug(() -> sphereRequest);
        logger.trace(() -> {
            final String output;
            if (httpRequest.getBody().isPresent() && httpRequest.getBody().get() instanceof StringHttpRequestBody) {
                final StringHttpRequestBody body = (StringHttpRequestBody) httpRequest.getBody().get();
                final String unformattedJson = body.getString();
                output = "send: " + unformattedJson + "\nformatted: " + JsonUtils.prettyPrintJsonStringSecure(unformattedJson);
            } else {
                output = "no request body present";
            }
            return output;
        });
        return httpClient.
                execute(httpRequest).
                thenApply(preProcess(sphereRequest, objectMapper, config));
    }

    private <T> HttpRequest createHttpRequest(final SphereRequest<T> sphereRequest, final String token) {
        return sphereRequest
                .httpRequestIntent()
                .plusHeader("User-Agent", "SPHERE.IO JVM SDK " + BuildInfo.version())
                .plusHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .prefixPath("/" + config.getProjectKey())
                .toHttpRequest(config.getApiUrl());
    }

    static <T> Function<HttpResponse, T> preProcess(final SphereRequest<T> sphereRequest, final ObjectMapper objectMapper, final SphereApiConfig config) {
        return httpResponse -> {
            final SphereInternalLogger logger = getLogger(httpResponse);
            logger.debug(() -> httpResponse);
            logger.trace(() -> httpResponse.getStatusCode() + "\n" + httpResponse.getResponseBody().map(body -> JsonUtils.prettyPrintJsonStringSecure(bytesToString(body))).orElse("No body present.") + "\n");
            final T result;
            result = parse(httpResponse, sphereRequest, objectMapper, config);
            return result;
        };
    }

    static <T> T parse(final HttpResponse httpResponse, final SphereRequest<T> sphereRequest, final ObjectMapper objectMapper, final SphereApiConfig config) {
        final T result;
        if (!sphereRequest.canHandleResponse(httpResponse)) {
            final SphereException sphereException = createExceptionFor(httpResponse, sphereRequest, objectMapper, config);
            throw sphereException;
        } else {
            try {
                result = sphereRequest.resultMapper().apply(httpResponse);
            } catch (final JsonException e) {
                final byte[] bytes = httpResponse.getResponseBody().get();
                throw new JsonException("Cannot parse " + bytesToString(bytes), e);
            }
        }
        return result;
    }

    private static <T> SphereException createExceptionFor(final HttpResponse httpResponse, final SphereRequest<T> sphereRequest, final ObjectMapper objectMapper, final SphereApiConfig config) {
        final SphereException sphereException = ExceptionFactory.of().createException(httpResponse, sphereRequest, objectMapper);
        fillExceptionWithData(sphereRequest, httpResponse, sphereException, config);
        return sphereException;
    }

    private static <T> void fillExceptionWithData(final SphereRequest<T> sphereRequest, final HttpResponse httpResponse, final SphereException exception, final SphereApiConfig config) {
        exception.setSphereRequest(sphereRequest);
        exception.setUnderlyingHttpResponse(httpResponse);
        exception.setProjectKey(config.getProjectKey());
    }

    @Override
    protected void internalClose() {
        closeQuietly(tokenSupplier);
        closeQuietly(httpClient);
    }

    public static SphereClient of(final SphereApiConfig config, final HttpClient httpClient, final SphereAccessTokenSupplier tokenSupplier) {
        return new SphereClientImpl(config, tokenSupplier, httpClient);
    }
}
