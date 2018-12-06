package io.sphere.sdk.apiclient;

import io.sphere.sdk.client.SphereClientConfig;
import io.sphere.sdk.client.SphereClientConfigBuilder;
import io.sphere.sdk.client.SphereProjectScope;
import io.sphere.sdk.client.SphereScope;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class ApiClientImpl extends ApiClientImplBase {
    ApiClientImpl(ZonedDateTime createdAt, String id, LocalDate lastUsedAt, String name, String scope, String secret) {
        super(createdAt, id, lastUsedAt, name, scope, secret);
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
                '}';
    }
}
