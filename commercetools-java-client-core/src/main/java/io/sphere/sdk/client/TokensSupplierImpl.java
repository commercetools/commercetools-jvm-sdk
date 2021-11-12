package io.sphere.sdk.client;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.http.*;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.SphereException;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletionStage;

import static io.sphere.sdk.client.SphereAuth.AUTH_LOGGER;
import static io.sphere.sdk.http.HttpMethod.POST;
import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

/**
 * Component that can fetch platform access tokens.
 * Does not refresh them,
 */
final class TokensSupplierImpl extends AutoCloseableService implements TokensSupplier {

    private final SphereAuthConfig config;
    private final HttpClient httpClient;
    private final boolean closeHttpClient;
    private boolean isClosed = false;
    @Nullable
    private String username;//only for password flow required
    @Nullable
    private String password;//only for password flow required

    private final String userAgent;

    private TokensSupplierImpl(final SphereAuthConfig config, final HttpClient httpClient, final boolean closeHttpClient, final List<SolutionInfo> additionalSolutionInfos) {
        this.config = config;
        this.httpClient = httpClient;
        this.closeHttpClient = closeHttpClient;
        this.userAgent = UserAgentUtils.obtainUserAgent(httpClient, additionalSolutionInfos);
    }

    static TokensSupplier of(final SphereAuthConfig config, final HttpClient httpClient, final boolean closeHttpClient) {
        return new TokensSupplierImpl(config, httpClient, closeHttpClient, Collections.emptyList());
    }

    static TokensSupplier of(final SphereAuthConfig config, final HttpClient httpClient, final boolean closeHttpClient, final List<SolutionInfo> additionalSolutionInfos) {
        return new TokensSupplierImpl(config, httpClient, closeHttpClient, additionalSolutionInfos);
    }

    /**
     * Executes a http auth sphere request and fetches a new access token.
     * @return future of a token
     */
    @Override
    public CompletionStage<Tokens> get() {
        rejectExcutionIfClosed("Token supplier is already closed.");
        AUTH_LOGGER.debug(() -> isPasswordFlow() ? "Fetching new password flow token." : "Fetching new client credentials flow token.");
        final HttpRequest httpRequest = newRequest();
        final CompletionStage<HttpResponse> httpResponseStage = httpClient.execute(httpRequest);
        final CompletionStage<Tokens> result = httpResponseStage.thenApply((response) -> parseResponse(response, httpRequest));
        result.whenCompleteAsync(this::logTokenResult);
        return result;
    }

    private boolean isPasswordFlow() {
        return username != null;
    }

    private void logTokenResult(final Tokens nullableTokens, final Throwable nullableThrowable) {
        if (nullableTokens != null) {
            AUTH_LOGGER.debug(() -> "Successfully fetched token that expires in " + Optional.ofNullable(nullableTokens.getExpiresIn()).map(x -> x.toString()).orElse("an unknown time") + ".");
        } else {
            AUTH_LOGGER.error(() -> "Failed to fetch token.", nullableThrowable);
        }
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
        final String correlationId = String.join("/", config.getProjectKey(), UUID.randomUUID().toString());
        final HttpHeaders httpHeaders = HttpHeaders
                .of(HttpHeaders.AUTHORIZATION, "Basic " + encodedString)
                .plus(HttpHeaders.USER_AGENT, userAgent)
                .plus(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .plus(HttpHeaders.X_CORRELATION_ID, correlationId);
        final String projectKey = config.getProjectKey();
        final Map<String, String> data = new HashMap<>();
        data.put("grant_type", isPasswordFlow() ? "password" : "client_credentials");
        final String scopeValue = config.getRawScopes().stream()
                //.map(scope -> format("%s:%s", scope, projectKey))
                .collect(joining(" "));

        data.put("scope", scopeValue);
        if (isPasswordFlow()) {
            data.put("username", username);
            data.put("password", password);
        }
        final FormUrlEncodedHttpRequestBody body = FormUrlEncodedHttpRequestBody.ofStringMap(data);
        final String url = isPasswordFlow()
                ? config.getAuthUrl() + "/oauth/" + projectKey + "/customers/token"
                : config.getAuthUrl() + "/oauth/token";
        final HttpRequest httpRequest = HttpRequest.of(POST, url, httpHeaders, body);
        return httpRequest;
    }

    /** Parses Tokens from a response from the backend authorization service.
     * @param httpResponse Response from the authorization service.
     * @param httpRequest the request which belongs to the response
     */
    private Tokens parseResponse(final HttpResponse httpResponse, final HttpRequest httpRequest) {
        try {
            if ((httpResponse.getStatusCode() == 401 || httpResponse.getStatusCode() == 400) && httpResponse.getResponseBody() != null) {
                ClientErrorException exception = new UnauthorizedException(httpResponse.toString(),httpResponse.getStatusCode());
                try {
                    final JsonNode jsonNode = SphereJsonUtils.parse(httpResponse.getResponseBody());
                    final String error = jsonNode.get("error").asText();
                    if (error.equals("invalid_client")) {
                        exception = new InvalidClientCredentialsException(config);
                    }
                    if (error.equals("invalid_scope")) {
                        final String description = jsonNode.get("error_description").asText();
                        if (description.endsWith("suspended")) {
                            exception = new SuspendedProjectException(exception);
                        } else {
                            exception = new InvalidScopeException(exception);
                        }
                    }
                } catch (final JsonException e) {
                    exception = new UnauthorizedException(httpResponse.toString(), e,httpResponse.getStatusCode());
                }
                throw exception;
            } else if (httpResponse.getStatusCode() >= 300) {
                throw new SphereException("negative HTTP response", new HttpException("status code is " + httpResponse.getStatusCode()));
            }
            try {
                return SphereJsonUtils.readObject(httpResponse.getResponseBody(), Tokens.typeReference());
            } catch (final SphereException e) {
                throw e;
            } catch (final Exception e) {
                throw new SphereException(e);
            }
        } catch (final SphereException exception) {
            exception.setProjectKey(config.getProjectKey());
            exception.setUnderlyingHttpResponse(httpResponse);
            exception.setHttpRequest(httpRequest);
            throw exception;
        }
    }

    public static TokensSupplier ofCustomerPasswordFlowTokensImpl(final SphereAuthConfig authConfig, final String email,
                                                                  final String password, final HttpClient httpClient,
                                                                  final boolean closeHttpClient) {
        final TokensSupplierImpl tokensSupplier = new TokensSupplierImpl(authConfig, httpClient, closeHttpClient, Collections.emptyList());
        tokensSupplier.username = email;
        tokensSupplier.password = password;
        return tokensSupplier;
    }

    public static TokensSupplier ofCustomerPasswordFlowTokensImpl(final SphereAuthConfig authConfig, final String email,
                                                                  final String password, final HttpClient httpClient,
                                                                  final boolean closeHttpClient,
                                                                  final List<SolutionInfo> additionalSolutionInfos) {
        final TokensSupplierImpl tokensSupplier = new TokensSupplierImpl(authConfig, httpClient, closeHttpClient, additionalSolutionInfos);
        tokensSupplier.username = email;
        tokensSupplier.password = password;
        return tokensSupplier;
    }
}
