package io.sphere.sdk.client;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.http.*;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.json.JsonUtils;
import io.sphere.sdk.utils.MapUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static io.sphere.sdk.client.SphereAuth.AUTH_LOGGER;
import static io.sphere.sdk.http.HttpMethod.POST;
import static java.lang.String.format;

/**
 * Component that can fetch SPHERE.IO access tokens.
 * Does not refresh them,
 */
final class TokensSupplierImpl extends AutoCloseableService implements TokensSupplier {

    private final SphereAuthConfig config;
    private final HttpClient httpClient;
    private final boolean closeHttpClient;
    private boolean isClosed = false;

    private TokensSupplierImpl(final SphereAuthConfig config, final HttpClient httpClient, final boolean closeHttpClient) {
        this.config = config;
        this.httpClient = httpClient;
        this.closeHttpClient = closeHttpClient;
    }

    static TokensSupplier of(final SphereAuthConfig config, final HttpClient httpClient, final boolean closeHttpClient) {
        return new TokensSupplierImpl(config, httpClient, closeHttpClient);
    }

    /**
     * Executes a http auth sphere request and fetches a new access token.
     * @return future of a token
     */
    @Override
    public CompletionStage<Tokens> get() {
        AUTH_LOGGER.debug(() -> "Fetching new token.");
        final CompletionStage<Tokens> result = httpClient.execute(newRequest()).thenApply(this::parseResponse);
        logTokenResult(result);
        return result;
    }

    private void logTokenResult(final CompletionStage<Tokens> result) {
        result.whenComplete((tokens, e) -> {
            if (tokens != null) {
                AUTH_LOGGER.debug(() -> "Successfully fetched token that expires in " + tokens.getExpiresIn().map(x -> x.toString()).orElse("an unknown time") + ".");
            } else {
                AUTH_LOGGER.error(() -> "Failed to fetch token." + tokens.getExpiresIn(), e);
            }
        });
    }

    @Override
    protected synchronized void internalClose() {
        if (!isClosed) {
            if (closeHttpClient) {
                closeQuietly(httpClient);
            }
            isClosed = true;
        }
    }

    private HttpRequest newRequest() {
        final String usernamePassword = format("%s:%s", config.getClientId(), config.getClientSecret());
        final String encodedString = Base64.getEncoder().encodeToString(usernamePassword.getBytes(StandardCharsets.UTF_8));
        final HttpHeaders httpHeaders = HttpHeaders
                .of(HttpHeaders.AUTHORIZATION, "Basic " + encodedString)
                .plus(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
        final FormUrlEncodedHttpRequestBody body = FormUrlEncodedHttpRequestBody.of(MapUtils.mapOf("grant_type", "client_credentials", "scope", format("manage_project:%s", config.getProjectKey())));
        return HttpRequest.of(POST, config.getAuthUrl() + "/oauth/token", httpHeaders, Optional.of(body));
    }

    /** Parses Tokens from a response from the backend authorization service.
     *  @param response Response from the authorization service.
     */
    private Tokens parseResponse(final HttpResponse response) {
        if (response.getStatusCode() == 401 && response.getResponseBody().isPresent()) {
            UnauthorizedException authorizationException = new UnauthorizedException(response.toString());
            try {
                final JsonNode jsonNode = JsonUtils.readTree(response.getResponseBody().get());
                if (jsonNode.get("error").asText().equals("invalid_client")) {
                    authorizationException = new InvalidClientCredentialsException(config);
                }
            } catch (final JsonException e) {
                authorizationException = new UnauthorizedException(response.toString(), e);
            }
            authorizationException.setProjectKey(config.getProjectKey());
            authorizationException.setUnderlyingHttpResponse(response);
            throw authorizationException;
        }
        return JsonUtils.readObject(Tokens.typeReference(), response.getResponseBody().get());
    }
}
