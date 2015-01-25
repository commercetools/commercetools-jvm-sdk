package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

import java.util.Optional;

final class AccessToken extends Base {
    private final String accessToken;
    private final Optional<Long> originalExpiresInSeconds;
    /** Time the tokens stored inside this instance were last refreshed, in System.currentTimeMillis(). */
    private final long updatedTimestamp;

    public AccessToken(String accessToken, Optional<Long> originalExpiresInSeconds, long updatedTimestamp) {
        this.accessToken = accessToken;
        this.originalExpiresInSeconds = originalExpiresInSeconds;
        this.updatedTimestamp = updatedTimestamp;
    }

    public String getAccessToken() { return accessToken; }

    public Optional<Long> getOriginalExpiresInSeconds() { return originalExpiresInSeconds; }

    public long getUpdatedTimestamp() { return updatedTimestamp; }

    public Optional<Long> getRemaniningMs() {
        return getOriginalExpiresInSeconds().map(originalExpiresInSec -> {
            long expiresAtMs = updatedTimestamp + 1000 * originalExpiresInSec;
            return expiresAtMs - System.currentTimeMillis();
        });
    }
}
