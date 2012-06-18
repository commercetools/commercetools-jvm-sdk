package sphere.util;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import play.mvc.Result;
import play.libs.F;
import play.libs.WS;
import play.mvc.Results;

import java.util.HashMap;
import java.util.Map;

public class OAuthClient {
    // RandomStringUtils.randomAlphanumeric(8) // might be useful later for nonces etc.

    /** Asynchronously gets access and refresh tokens for given user from the authorization server
     *  using the Resource owner credentials flow. */
    public static <R> F.Promise<R> getTokensForClient(
            final String tokenEndpoint, final String clientID, final String clientSecret,
            final F.Function<ServiceError, R> onError,
            final F.Function<Tokens, R> onSuccess)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("grant_type", "client_credentials");
        String authHeader = Headers.encodeBasicAuthHeader(clientID, clientSecret);
        return
        WS.url(tokenEndpoint)
            .setHeader("Authorization", authHeader)
            .setHeader("Content-Type", "application/x-www-form-urlencoded")
            .post(Url.buildQueryString(params))
            .map(new F.Function<WS.Response, R>() {
                @Override
                public R apply(WS.Response resp) throws Throwable {
                    if (resp.getStatus() != 200) {
                        return onError.apply(new ServiceError(ServiceErrorType.Other, resp.getBody()));
                    }
                    JsonNode json = new ObjectMapper().readValue(resp.getBody(), JsonNode.class);
                    String accessToken = json.path("access_token").getTextValue();
                    if (accessToken == null) {
                        return onError.apply(
                            new ServiceError(ServiceErrorType.UnexpectedError,
                                    "The authorization server did not return access token."
                            ));
                    } else {
                        return onSuccess.apply(Tokens.fromJson(json));
                    }
                }
            });
    }

    /** Asynchronously gets access and refresh tokens for given user from the authorization server
     *  using the Resource owner credentials flow. */
    public static Results.AsyncResult getTokensForUser(
        final String tokenEndpoint, final String clientID, final String clientSecret, final String username, final String password,
        final F.Function<ServiceError, Result> onError,
        final F.Function<Tokens, Result> onSuccess)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);
        String authHeader = Headers.encodeBasicAuthHeader(clientID, clientSecret);
        return
        Results.async(
            WS.url(tokenEndpoint)
                .setHeader("Authorization", authHeader)
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(Url.buildQueryString(params))
                .map(new F.Function<WS.Response, Result>() {
                    @Override
                    public Result apply(WS.Response resp) throws Throwable {
                        if (resp.getStatus() != 200) {
                            return onError.apply(new ServiceError(ServiceErrorType.Other, resp.getBody()));
                        }
                        JsonNode json = new ObjectMapper().readValue(resp.getBody(), JsonNode.class);
                        String accessToken = json.path("access_token").getTextValue();
                        if (accessToken == null) {
                            return onError.apply(
                                new ServiceError(ServiceErrorType.UnexpectedError,
                                "The authorization server did not return access token."
                            ));
                        }
                        return onSuccess.apply(Tokens.fromJson(json));
                    }
                })
        );
    }
}