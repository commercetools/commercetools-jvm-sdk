package io.sphere.client.oauth;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import io.sphere.client.SphereClientException;
import net.jcip.annotations.Immutable;

/** OAuth tokens returned by the authorization server. */
@Immutable
public final class Tokens {
    private final String accessToken;
    private final String refreshToken;
    private final Optional<Long> expiresIn;

    public Tokens(String accessToken, String refreshToken, Optional<Long> expiresIn) {
        if (Strings.isNullOrEmpty(accessToken))
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
