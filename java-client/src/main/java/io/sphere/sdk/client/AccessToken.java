package io.sphere.sdk.client;

import com.google.common.base.Function;
import com.google.common.base.Optional;

/** Helper class for {@link io.sphere.sdk.client.SphereClientCredentials}. */
class AccessToken {
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
        return getOriginalExpiresInSeconds().transform(new Function<Long, Long>() {
            public Long apply(Long originalExpiresInSec) {
                long expiresAtMs = updatedTimestamp + 1000 * originalExpiresInSec;
                return expiresAtMs - System.currentTimeMillis();
            }
        });
    }
}
