package io.sphere.sdk.client;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/** OAuth tokens returned by the authorization server. */
final class Tokens {
    private final String accessToken;
    private final String refreshToken;
    private final Optional<Long> expiresIn;

    public Tokens(String accessToken, String refreshToken, Optional<Long> expiresIn) {
        if (isEmpty(accessToken))
            throw new SphereClientException("OAuth response must contain an access_token. Was empty.");
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
