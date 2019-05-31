package io.sphere.sdk.customers;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import java.time.ZonedDateTime;

class CustomerTokenImpl extends Base implements CustomerToken {
    private final String id;
    private final String customerId;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime lastModifiedAt;
    private final String value;
    private final ZonedDateTime expiresAt;

    @JsonCreator
    public CustomerTokenImpl(final String id, final String customerId, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final String value, final ZonedDateTime expiresAt) {
        this.id = id;
        this.customerId = customerId;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.value = value;
        this.expiresAt = expiresAt;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getCustomerId() {
        return customerId;
    }

    @Override
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public ZonedDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public ZonedDateTime getExpiresAt() {
        return expiresAt;
    }
}
