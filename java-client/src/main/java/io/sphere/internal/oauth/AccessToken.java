package io.sphere.internal.oauth;

import com.google.common.base.Function;
import com.google.common.base.Optional;

/** Helper class for {@link SphereClientCredentials}. */
public class AccessToken {
    private String accessToken;
    private Optional<Long> originalExpiresInSeconds;
    /** Time the tokens stored inside this instance were last refreshed, in System.currentTimeMillis(). */
    private long updatedTimestamp;

    public AccessToken(String accessToken, Optional<Long> originalExpiresInSeconds, long updatedTimestamp) {
        this.accessToken = accessToken;
        this.originalExpiresInSeconds = originalExpiresInSeconds;
        this.updatedTimestamp = updatedTimestamp;
    }

    public String accessToken() {
        return accessToken;
    }

    public Optional<Long> originalExpiresInSeconds() {
        return originalExpiresInSeconds;
    }

    public long updatedTimestamp() {
        return updatedTimestamp;
    }

    public Optional<Long> remainingMs() {
        return originalExpiresInSeconds().transform(new Function<Long, Long>() {
            public Long apply(Long originalExpiresInSec) {
                long expiresAtMs = updatedTimestamp + 1000 * originalExpiresInSec;
                long remainingMs = expiresAtMs - System.currentTimeMillis();
                return remainingMs; }});
    }
}
