package io.sphere.sdk.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sphere.sdk.http.*;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.SphereException;
import io.sphere.sdk.utils.CompletableFutureUtils;
import io.sphere.sdk.utils.SphereInternalLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static io.sphere.sdk.client.HttpResponseBodyUtils.bytesToString;
import static io.sphere.sdk.utils.SphereInternalLogger.getLogger;

final class SphereClientImpl extends AutoCloseableService implements SphereClient {
    private static final Logger classLogger = LoggerFactory.getLogger(SphereClient.class);
    private final ObjectMapper objectMapper = SphereJsonUtils.newObjectMapper();
    private final HttpClient httpClient;
    private final SphereApiConfig config;
    private final SphereAccessTokenSupplier tokenSupplier;
    private final String userAgent;
    private final CorrelationIdGenerator correlationIdGenerator;

    private SphereClientImpl(final SphereApiConfig config, final SphereAccessTokenSupplier tokenSupplier,
                             final HttpClient httpClient, final CorrelationIdGenerator correlationIdGenerator, final List<SolutionInfo> additionalSolutionInfos) {
        this.httpClient = httpClient;
        this.config = config;
        this.tokenSupplier = tokenSupplier;
        this.userAgent = UserAgentUtils.obtainUserAgent(httpClient, additionalSolutionInfos);
        this.correlationIdGenerator = correlationIdGenerator;
    }

    @Override
    public <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest) {
        rejectExcutionIfClosed("Client is already closed.");
        try {
            final int ttl = 1;
            return tokenSupplier.get().thenComposeAsync(token -> execute(sphereRequest, token, ttl));
        } catch (final Throwable throwable) {
            return CompletableFutureUtils.failed(throwable);
        }
    }

    private <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest, final String token, final int ttl) {
        final HttpRequest httpRequest = createHttpRequest(sphereRequest, token);
        final SphereInternalLogger logger = getLogger(httpRequest);
        logger.debug(() -> sphereRequest);
        logger.trace(() -> {
            final String output;
            final String httpMethodAndUrl = httpRequest.getHttpMethod() + " " + httpRequest.getUrl();
            if (httpRequest.getBody() != null && httpRequest.getBody() instanceof StringHttpRequestBody) {
                final StringHttpRequestBody body = (StringHttpRequestBody) httpRequest.getBody();
                final String unformattedBody = body.getSecuredBody();
                final boolean isJsonRequest = httpRequest.getHeaders().findFlatHeader(HttpHeaders.CONTENT_TYPE).map(ct -> ct.toLowerCase().contains("json")).orElse(true);
                if (isJsonRequest) {
                    String prettyPrint;
                    try {
                        prettyPrint = SphereJsonUtils.prettyPrint(unformattedBody);
                    } catch (final JsonException e) {
                        classLogger.warn("pretty print failed", e);
                        prettyPrint = unformattedBody;
                    }
                    output = "send: " + httpMethodAndUrl + "\nformatted: " + prettyPrint;
                } else {
                    output = "send: " + httpRequest.getHttpMethod() + " " + httpRequest.getUrl() + " " + unformattedBody;
                }
            } else {
                output = httpMethodAndUrl + " <no body>";
            }
            return output;
        });
        return executeWithRecover(sphereRequest, httpRequest, ttl);
    }

    private <T> CompletableFuture<T> executeWithRecover(final SphereRequest<T> sphereRequest, final HttpRequest httpRequest, final int ttl) {
        final CompletionStage<T> intermediateResult = httpClient.execute(httpRequest).thenApplyAsync(httpResponse -> {
            try {
                return processHttpResponse(sphereRequest, objectMapper, config, httpResponse, httpRequest);
            } catch (final SphereException e) {
                fillExceptionWithData(sphereRequest, httpResponse, e, config, httpRequest);
                throw e;
            }
        });
        final CompletableFuture<T> result = new CompletableFuture<T>();
        intermediateResult.whenCompleteAsync((value, throwable) -> {
            if (throwable != null) {
                if (throwable.getCause() instanceof InvalidTokenException && ttl > 0 && tokenSupplier instanceof RefreshableSphereAccessTokenSupplier) {
                    final RefreshableSphereAccessTokenSupplier supplier = (RefreshableSphereAccessTokenSupplier) tokenSupplier;
                    final CompletionStage<T> nextAttemptCompletionStage = supplier.getNewToken().thenComposeAsync(token -> execute(sphereRequest, token, ttl - 1));
                    CompletableFutureUtils.transferResult(nextAttemptCompletionStage, result);
                } else {
                    result.completeExceptionally(throwable);
                }
            } else {
                result.complete(value);
            }
        });
        return result;
    }

    private <T> HttpRequest createHttpRequest(final SphereRequest<T> sphereRequest, final String token) {
        final String correlationId = correlationIdGenerator.get();
        return sphereRequest
                .httpRequestIntent()
                .plusHeader(HttpHeaders.X_CORRELATION_ID, correlationId)
                .plusHeader(HttpHeaders.USER_AGENT, userAgent)
                .plusHeader(HttpHeaders.ACCEPT_ENCODING, "gzip")
                .plusHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .prefixPath("/" + config.getProjectKey())
                .toHttpRequest(config.getApiUrl());
    }

    private static <T> T processHttpResponse(final SphereRequest<T> sphereRequest, final ObjectMapper objectMapper, final SphereApiConfig config, final HttpResponse httpResponse, final HttpRequest httpRequest) {
        final SphereInternalLogger logger = getLogger(httpResponse);
        logger.debug(() -> httpResponse);
        logger.trace(() -> httpResponse.getStatusCode() + "\n" + Optional.ofNullable(httpResponse.getResponseBody()).map(body -> SphereJsonUtils.prettyPrint(bytesToString(body))).orElse("No body present."));
        final List<String> notices = httpResponse.getHeaders().getHeadersAsMap().get(SphereHttpHeaders.X_DEPRECATION_NOTICE);
        if (notices != null) {
            notices.forEach(message -> logger.warn(() -> "Deprecation notice : " + message));
        }
        return parse(sphereRequest, objectMapper, config, httpResponse, httpRequest);
    }

    static <T> T parse(final SphereRequest<T> sphereRequest, final ObjectMapper objectMapper, final SphereApiConfig config, final HttpResponse httpResponse, final HttpRequest httpRequest) {
        final T result;
        if (!sphereRequest.canDeserialize(httpResponse)) {
            final SphereException sphereException = createExceptionFor(httpResponse, sphereRequest, objectMapper, config, httpRequest);
            throw sphereException;
        } else {
            try {
                result = sphereRequest.deserialize(httpResponse);
            } catch (final JsonException e) {
                final byte[] bytes = httpResponse.getResponseBody();
                e.addNote("Cannot parse " + bytesToString(bytes));
                throw e;
            }
        }
        return result;
    }

    private static <T> SphereException createExceptionFor(final HttpResponse httpResponse, final SphereRequest<T> sphereRequest, final ObjectMapper objectMapper, final SphereApiConfig config, final HttpRequest httpRequest) {
        final SphereException sphereException = ExceptionFactory.of().createException(httpResponse, sphereRequest, objectMapper);
        fillExceptionWithData(sphereRequest, httpResponse, sphereException, config, httpRequest);
        return sphereException;
    }

    private static <T> void fillExceptionWithData(final SphereRequest<T> sphereRequest, final HttpResponse httpResponse, final SphereException exception, final SphereApiConfig config, final HttpRequest httpRequest) {
        exception.setSphereRequest(sphereRequest);
        exception.setUnderlyingHttpResponse(httpResponse);
        exception.setProjectKey(config.getProjectKey());
        exception.setHttpRequest(httpRequest);
    }

    @Override
    protected void internalClose() {
        closeQuietly(tokenSupplier);
        closeQuietly(httpClient);
    }

    public static SphereClient of(final SphereApiConfig config, final HttpClient httpClient,
                                  final SphereAccessTokenSupplier tokenSupplier) {
        return new SphereClientImpl(config, tokenSupplier, httpClient, config.getCorrelationIdGenerator(), Collections.emptyList());
    }

    public static SphereClient of(final SphereApiConfig config, final HttpClient httpClient,
                                  final SphereAccessTokenSupplier tokenSupplier, final CorrelationIdGenerator correlationIdGenerator) {
        return new SphereClientImpl(config, tokenSupplier, httpClient, correlationIdGenerator, Collections.emptyList());
    }

    public static SphereClient of(final SphereApiConfig config, final HttpClient httpClient,
                                  final SphereAccessTokenSupplier tokenSupplier,
                                  final List<SolutionInfo> additionalSolutionInfos) {
        return new SphereClientImpl(config, tokenSupplier, httpClient, config.getCorrelationIdGenerator(), additionalSolutionInfos);
    }

    public static SphereClient of(final SphereApiConfig config, final HttpClient httpClient,
                                  final SphereAccessTokenSupplier tokenSupplier, final CorrelationIdGenerator correlationIdGenerator,
                                  final List<SolutionInfo> additionalSolutionInfos) {
        return new SphereClientImpl(config, tokenSupplier, httpClient, correlationIdGenerator, additionalSolutionInfos);
    }

    @Override
    public SphereApiConfig getConfig() {
        return config;
    }
}
