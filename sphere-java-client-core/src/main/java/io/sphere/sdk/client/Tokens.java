package io.sphere.sdk.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.SphereException;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/** OAuth tokens returned by the authorization server. */
final class Tokens extends Base {
    @JsonProperty("access_token")
    private final String accessToken;
    @JsonProperty("refresh_token")
    private final String refreshToken;
    @JsonProperty("expires_in")
    @Nullable
    private final Long expiresIn;
    @JsonIgnore
    @Nullable
    private final ZonedDateTime expiresInZonedDateTime;

    @JsonCreator
    private Tokens(String accessToken, String refreshToken, @Nullable Long expiresIn) {
        if (isEmpty(accessToken))
            throw new SphereException("OAuth response must contain an access_token. Was empty.");
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        expiresInZonedDateTime = Optional.ofNullable(expiresIn).map(seconds -> ZonedDateTime.now().plusSeconds(seconds)).orElse(null);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    @Nullable
    public Long getExpiresIn() {
        return expiresIn;
    }

    @Nullable
    ZonedDateTime getExpiresZonedDateTime() {
        return expiresInZonedDateTime;
    }

    public static TypeReference<Tokens> typeReference() {
        return new TypeReference<Tokens>() {
            @Override
            public String toString() {
                return "TypeReference<Tokens>";
            }
        };
    }

    @JsonIgnore
    public static Tokens of(String accessToken, String refreshToken, Long expiresIn) {
        return new Tokens(accessToken, refreshToken, expiresIn);
    }
}
