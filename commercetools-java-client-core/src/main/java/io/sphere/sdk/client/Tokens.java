package io.sphere.sdk.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/** OAuth tokens returned by the authorization server. */
@JsonDeserialize(as = TokensImpl.class)
public interface Tokens {
    String getAccessToken();

    @Nullable
    String getRefreshToken();

    @Nullable
    Long getExpiresIn();

    @Nullable
    ZonedDateTime getExpiresZonedDateTime();

    /**
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<Tokens> typeReference() {
        return new TypeReference<Tokens>() {
            @Override
            public String toString() {
                return "TypeReference<Tokens>";
            }
        };
    }

    @JsonIgnore
    static Tokens of(String accessToken, String refreshToken, Long expiresIn) {
        return new TokensImpl(accessToken, refreshToken, expiresIn);
    }
}
