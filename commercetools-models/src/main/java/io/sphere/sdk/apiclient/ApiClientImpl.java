package io.sphere.sdk.apiclient;

import java.time.ZonedDateTime;

class ApiClientImpl extends ApiClientImplBase {
    ApiClientImpl(ZonedDateTime createdAt, String id, ZonedDateTime lastUsedAt, String name, String scope, String secret) {
        super(createdAt, id, lastUsedAt, name, scope, secret);
    }

    /**
     * Overriding to string to hide secret on logs
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
