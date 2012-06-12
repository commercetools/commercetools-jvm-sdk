package sphere.util;

import org.codehaus.jackson.JsonNode;

/** OAuth tokens returned by the authorization server. */
public class Tokens {
    private final String accessToken;
    private final String refreshToken;
    private final int expiresIn;
    private final boolean _hasExpiresIn;

    /** Parses tokens from the standard JSON response returned by OAuth authorization server.. */
    public static Tokens fromJson(JsonNode json) {
        String accessToken = json.path("access_token").getTextValue();
        if (accessToken == null) {
            throw new IllegalArgumentException("access_token missing.");
        }
        boolean hasExpiresIn = json.path("expires_in").isNumber();
        int expiresIn = hasExpiresIn ? json.path("expires_in").getIntValue() : -1;
        // Refresh token might not be returned by the authorization server.
        String refreshToken = json.path("refresh_token").getTextValue();
        return new Tokens(accessToken, refreshToken, expiresIn, hasExpiresIn);
    }  

    public Tokens(String accessToken, String refreshToken, int expiresIn, boolean hasExpiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this._hasExpiresIn = hasExpiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
    public int getExpiresIn() {
        return expiresIn;
    }
    public boolean hasExpiresIn() {
        return _hasExpiresIn;
    }
}