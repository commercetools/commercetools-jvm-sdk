package io.sphere.sdk.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sphere.sdk.errors.*;
import io.sphere.sdk.errors.ConcurrentModificationException;
import io.sphere.sdk.errors.JsonException;
import io.sphere.sdk.errors.NotFoundException;
import io.sphere.sdk.http.*;
import io.sphere.sdk.meta.BuildInfo;
import io.sphere.sdk.errors.SphereException;
import io.sphere.sdk.utils.JsonUtils;
import io.sphere.sdk.utils.SphereInternalLogger;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

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
        final SphereRequest<T> usedClientRequest = CachedSphereRequest.of(sphereRequest);
        return tokenFuture.thenCompose(token -> execute(usedClientRequest, token));
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
        return new Function<HttpResponse, T>() {
            @Override
            public T apply(final HttpResponse httpResponse) {
                final SphereInternalLogger logger = getLogger(httpResponse);
                logger.debug(() -> httpResponse);
                logger.trace(() -> httpResponse.getStatusCode() + "\n" + httpResponse.getResponseBody().map(body -> JsonUtils.prettyPrintJsonStringSecure(bytesToString(body))).orElse("No body present.") + "\n");
                final T result;
                result = parse(httpResponse, sphereRequest, objectMapper, config);
                return result;
            }

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
            } catch (final io.sphere.sdk.errors.JsonException e) {
                final byte[] bytes = httpResponse.getResponseBody().get();
                throw new JsonException("Cannot parse " + bytesToString(bytes), e);
            }
        }
        return result;
    }

    private static <T> SphereException createExceptionFor(final HttpResponse httpResponse, final SphereRequest<T> sphereRequest, final ObjectMapper objectMapper, final SphereApiConfig config) {
        final SphereException sphereException = createFlatException(httpResponse, sphereRequest, objectMapper);
        fillExceptionWithData(sphereRequest, httpResponse, sphereException, config);
        return sphereException;
    }

    private static <T> SphereException createFlatException(final HttpResponse httpResponse, final SphereRequest<T> sphereRequest, final ObjectMapper objectMapper) {
        //TODO reorder, most common up, or use map and key value search
        if (isServiceNotAvailable(httpResponse)) {
            return new ServiceUnavailableException();
        } else if(httpResponse.getStatusCode() == 401) {
            return new InvalidTokenException();
        } else if(httpResponse.getStatusCode() == 500) {
            return new InternalServerErrorException();
        } else if(httpResponse.getStatusCode() == 502) {
            return new BadGatewayException();
        } else if(httpResponse.getStatusCode() == 503) {
            return new ServiceUnavailableException();
        } else if(httpResponse.getStatusCode() == 504) {
            return new GatewayTimeoutException();
        } else if (httpResponse.getStatusCode() == 409) {
            return new ConcurrentModificationException();
        } else if (httpResponse.getStatusCode() == 400 && httpResponse.getResponseBody().isPresent()) {
            final ErrorResponse errorResponse = JsonUtils.readObject(ErrorResponse.typeReference(), httpResponse.getResponseBody().get());
            final SphereErrorResponseToExceptionMapper exceptionMapper = SphereErrorResponseToExceptionMapper.of();
            final SphereException exception = exceptionMapper.toException(errorResponse);
            return exception;
        } else if (httpResponse.getStatusCode() == 404) {
            return new NotFoundException();
        } else {
            //TODO maybe SphereException or JsonException more appropriate
            return new SphereException("Can't parse backend response.");
        }
    }

    //hack since backend returns in same error conditions responce code 500 but with the message Service unavailable
    private static boolean isServiceNotAvailable(final HttpResponse httpResponse) {
        return httpResponse.getStatusCode() == 503 || httpResponse.getResponseBody().map(b -> bytesToString(b)).map(s -> s.contains("<h2>Service Unavailable</h2>")).orElse(false);
    }

    private static String bytesToString(final byte[] b) {
        return new String(b, StandardCharsets.UTF_8);
    }

    private static boolean isErrorResponse(final HttpResponse httpResponse) {
        return httpResponse.getStatusCode() / 100 != 2;
    }

    private static <T> void fillExceptionWithData(final SphereRequest<T> sphereRequest, final HttpResponse httpResponse, final SphereException exception, final SphereApiConfig config) {
        exception.setSphereRequest(sphereRequest);
        exception.setUnderlyingHttpRequest(sphereRequest.httpRequestIntent().toString());
        exception.setUnderlyingHttpResponse(httpResponse.withoutRequest().toString());
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
