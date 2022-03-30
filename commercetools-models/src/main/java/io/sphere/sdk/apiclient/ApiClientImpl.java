package io.sphere.sdk.apiclient;

import io.sphere.sdk.client.SphereClientConfig;
import io.sphere.sdk.client.SphereClientConfigBuilder;
import io.sphere.sdk.client.SphereProjectScope;
import io.sphere.sdk.client.SphereScope;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class ApiClientImpl extends ApiClientImplBase {

    public ApiClientImpl(final @Nullable ZonedDateTime createdAt, final @Nullable ZonedDateTime deleteAt, final String id, final @Nullable LocalDate lastUsedAt, final String name, final String scope, final @Nullable String secret, final @Nullable Long accessTokenValiditySeconds, final @Nullable Long refreshTokenValiditySeconds) {
        super(accessTokenValiditySeconds, createdAt, deleteAt, id, lastUsedAt, name, refreshTokenValiditySeconds, scope, secret);
    }

    @Override
    public String getProjectKey() {
        return getScope().split("\\s")[0].split(":")[1];
    }

    /**
     * Overriding toString to hide secret on logs
     * @return string representation of the an ApiClient
     */
    @Override
    public String toString() {
        return "ApiClientImplBase{" +
                "createdAt=" + getCreatedAt() +
                ", id='" + getId() + '\'' +
                ", lastUsedAt=" + getLastUsedAt() +
                ", name='" + getName() + '\'' +
                ", scope='" + getScope() + '\'' +
                ", secret='*************'" +
                ", accessTokenValiditySeconds='" + getAccessTokenValiditySeconds() + '\'' +
                ", refreshTokenValiditySeconds='" + getRefreshTokenValiditySeconds() + '\'' +
                '}';
    }
}
