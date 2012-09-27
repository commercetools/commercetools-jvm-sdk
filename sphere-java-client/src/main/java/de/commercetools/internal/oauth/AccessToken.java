package de.commercetools.internal.oauth;

import com.google.common.base.Function;
import com.google.common.base.Optional;

import javax.annotation.Nullable;

/** Helper class for {@link ShopClientCredentials}. */
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
            @Override
            public Long apply(@Nullable Long originalExpiresInSec) {
                long expiresAt = updatedTimestamp + 1000 * originalExpiresInSec;
                long remainingMs = expiresAt - System.currentTimeMillis();
                return remainingMs; }});
    }
}
