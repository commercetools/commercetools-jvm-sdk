package io.sphere.sdk.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.http.*;
import io.sphere.sdk.meta.BuildInfo;
import io.sphere.sdk.models.SphereException;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.utils.SphereInternalLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static io.sphere.sdk.client.HttpResponseBodyUtils.bytesToString;
import static io.sphere.sdk.utils.SphereInternalLogger.getLogger;
import static java.lang.String.format;
import static java.util.Arrays.asList;

final class SphereClientImpl extends AutoCloseableService implements SphereClient {
    private static final Logger classLogger = LoggerFactory.getLogger(SphereClient.class);
    private final ObjectMapper objectMapper = SphereJsonUtils.newObjectMapper();
    private final HttpClient httpClient;
    private final SphereApiConfig config;
    private final SphereAccessTokenSupplier tokenSupplier;


    private SphereClientImpl(final SphereApiConfig config, final SphereAccessTokenSupplier tokenSupplier, final HttpClient httpClient) {
        this.httpClient = httpClient;
        this.config = config;
        this.tokenSupplier = tokenSupplier;
    }

    @Override
    public <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest) {
        final CompletionStage<String> tokenFuture = tokenSupplier.get();
        return tokenFuture.thenCompose(token -> execute(sphereRequest, token));
    }

    private <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest, final String token) {
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
        return httpClient.execute(httpRequest).thenApply(httpResponse -> {
            try {
                return processHttpResponse(sphereRequest, objectMapper, config, httpResponse);
            } catch (final SphereException e) {
                fillExceptionWithData(sphereRequest, httpResponse, e, config);
                throw e;
            }
        });
    }

    private <T> HttpRequest createHttpRequest(final SphereRequest<T> sphereRequest, final String token) {
        return sphereRequest
                .httpRequestIntent()
                .plusHeader(HttpHeaders.USER_AGENT, BuildInfo.userAgent())
                .plusHeader(HttpHeaders.ACCEPT_ENCODING, "gzip")
                .plusHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .prefixPath("/" + config.getProjectKey())
                .toHttpRequest(config.getApiUrl());
    }

    private static <T> T processHttpResponse(final SphereRequest<T> sphereRequest, final ObjectMapper objectMapper, final SphereApiConfig config, final HttpResponse httpResponse) {
        final SphereInternalLogger logger = getLogger(httpResponse);
        logger.debug(() -> httpResponse);
        logger.trace(() -> httpResponse.getStatusCode() + "\n" + Optional.ofNullable(httpResponse.getResponseBody()).map(body -> SphereJsonUtils.prettyPrint(bytesToString(body))).orElse("No body present."));
        final List<String> notices = httpResponse.getHeaders().getHeadersAsMap().get(SphereHttpHeaders.X_DEPRECATION_NOTICE);
        if (notices != null) {
            notices.stream().forEach(message -> logger.warn(() -> "Deprecation notice : " + message));
        }
        return parse(sphereRequest, objectMapper, config, httpResponse);
    }

    static <T> T parse(final SphereRequest<T> sphereRequest, final ObjectMapper objectMapper, final SphereApiConfig config, final HttpResponse httpResponse) {
        final T result;
        if (!sphereRequest.canDeserialize(httpResponse)) {
            final SphereException sphereException = createExceptionFor(httpResponse, sphereRequest, objectMapper, config);
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

    private static <T> SphereException createExceptionFor(final HttpResponse httpResponse, final SphereRequest<T> sphereRequest, final ObjectMapper objectMapper, final SphereApiConfig config) {
        final SphereException sphereException = ExceptionFactory.of().createException(httpResponse, sphereRequest, objectMapper);
        fillExceptionWithData(sphereRequest, httpResponse, sphereException, config);
        return sphereException;
    }

    private static <T> void fillExceptionWithData(final SphereRequest<T> sphereRequest, final HttpResponse httpResponse, final SphereException exception, final SphereApiConfig config) {
        exception.setSphereRequest(sphereRequest);
        exception.setUnderlyingHttpResponse(httpResponse);
        exception.setProjectKey(config.getProjectKey());

        final List<String> errorMessagesDueToMappingError = asList("SearchPhaseExecutionException", "query_fetch", "RemoteTransportException", "SearchParseException", "search/phase/query+fetch");
        Optional.ofNullable(httpResponse.getResponseBody())
                .map(bytes -> bytesToString(bytes))
                .map(body -> errorMessagesDueToMappingError.stream().anyMatch(errorWord -> body.contains(errorWord)) && body.toLowerCase().contains("product"))
                .ifPresent(containsTerm -> {
                    if (containsTerm) {
                        exception.addNote(format("Maybe it helps to reindex the products https://admin.sphere.io/%s/developers/danger but this may take a while.", config.getProjectKey()));
                    }
                });
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
