package sphere.util;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.apache.commons.lang.RandomStringUtils;
import play.mvc.Result;
import play.libs.F;
import play.libs.WS;
import play.mvc.Results;

import java.util.HashMap;
import java.util.Map;

public class OAuthClient {
    /** Constructs the authorization server's URI to which your app should redirect to obtain authorization.
     *  @param authUrl Base URL, such as "http://example.com/authorize" 
     * */
    public static String getAuthorizationEndpoint(String authUrl, String clientId, String scope) {
        // We'll need OpenId Connect to get user name and email
        String csrfState = RandomStringUtils.randomAlphanumeric(8);
        String nonce = RandomStringUtils.randomAlphanumeric(8);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("response_type", "code");
        params.put("client_id", clientId);
        params.put("scope", "openid profile " + scope);
        params.put("state", csrfState);  // TODO this has to be stored in current session
        params.put("nonce", nonce);
        return authUrl + "?" + Url.buildQueryString(params);
    }

    /** Asynchronously gets access and refresh tokens for given user from the authorization server. */
    public static Results.AsyncResult getTokenForUser(
        String tokenEndpoint, String clientId, String clientSecret, String username, String password,
        final F.Function<LoginError, Result> onError,
        final F.Function<Tokens, Result> onSuccess)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        String authHeader = Headers.encodeBasicAuthHeader(clientId, clientSecret);
        return
        Results.async(
            WS.url(tokenEndpoint)
            .setHeader("Authorization", authHeader)
            .setHeader("Content-Type", "application/x-www-form-urlencoded")
            .post(Url.buildQueryString(params)).map(new F.Function<WS.Response, Result>() {
                @Override
                public Result apply(WS.Response resp) throws Throwable {
                    if (resp.getStatus() != 200) {
                        return onError.apply(new LoginError(LoginErrorType.Other, resp.getBody()));
                    }
                    JsonNode json = new ObjectMapper().readValue(resp.getBody(), JsonNode.class);
                    String accessToken = json.path("access_token").getTextValue();
                    if (accessToken == null) {
                        return onError.apply(new LoginError(LoginErrorType.UnexpectedError, "The authorization server did not return access token."));
                    }
                    // Refresh token might not be returned by the authorization server.
                    String refreshToken = json.path("refresh_token").getTextValue();
                    return onSuccess.apply(new Tokens(accessToken, refreshToken));
                }
            })
        );
    }
}