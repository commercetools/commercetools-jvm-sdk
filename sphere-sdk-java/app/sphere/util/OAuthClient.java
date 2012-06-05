package sphere.util;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import play.mvc.Result;
import play.libs.F;
import play.libs.WS;
import play.mvc.Results;
import sphere.Config;

import java.util.HashMap;
import java.util.Map;

public class OAuthClient {
    // RandomStringUtils.randomAlphanumeric(8) // might be needed later for nonces etc.

    /** Asynchronously gets access and refresh tokens for given user from the authorization server. */
    public static Results.AsyncResult getTokenForUser(
        String username, String password,
        final F.Function<LoginError, Result> onError,
        final F.Function<Tokens, Result> onSuccess)
    {
        return getTokenForUser(Config.tokenEndpoint(),
            Config.projectID(), Config.projectSecret(), username, password, onError, onSuccess
        );
    }

    /** Asynchronously gets access and refresh tokens for given user from the authorization server
     *  using the Resource owner credentials flow. */
    public static Results.AsyncResult getTokenForUser(
        String tokenEndpoint, String clientID, String clientSecret, String username, String password,
        final F.Function<LoginError, Result> onError,
        final F.Function<Tokens, Result> onSuccess)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);
        params.put("client_id", clientID);
        params.put("client_secret", clientSecret);
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
                            return onError.apply(new LoginError(LoginErrorType.Other, resp.getBody()));
                        }
                        JsonNode json = new ObjectMapper().readValue(resp.getBody(), JsonNode.class);
                        String accessToken = json.path("access_token").getTextValue();
                        if (accessToken == null) {
                            return onError.apply(
                                new LoginError(LoginErrorType.UnexpectedError,
                                "The authorization server did not return access token."
                            ));
                        }
                        // Refresh token might not be returned by the authorization server.
                        String refreshToken = json.path("refresh_token").getTextValue();
                        return onSuccess.apply(new Tokens(accessToken, refreshToken));
                    }
                })
        );
    }
}