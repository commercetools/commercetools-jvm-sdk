package sphere.util;

import com.google.common.base.Optional;
import com.google.common.base.Strings;

/** OAuth tokens returned by the authorization server. */
public class OAuthTokens {
    private final String accessToken;
    private final String refreshToken;
    private final Optional<Long> expiresIn;

    public OAuthTokens(String accessToken, String refreshToken, Optional<Long> expiresIn) {
        if (Strings.isNullOrEmpty(accessToken))
            throw new IllegalArgumentException("OAuth tokens must contain an access_token. Was empty.");
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
    public Optional<Long> getExpiresIn() {
        return expiresIn;
    }
}