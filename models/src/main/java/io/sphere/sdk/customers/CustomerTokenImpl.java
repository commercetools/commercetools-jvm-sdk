package io.sphere.sdk.customers;

import io.sphere.sdk.models.Base;

import java.time.Instant;

class CustomerTokenImpl extends Base implements CustomerToken {
    private final String id;
    private final String customerId;
    private final Instant createdAt;
    private final Instant lastModifiedAt;
    private final String value;

    public CustomerTokenImpl(final String id, final String customerId, final Instant createdAt, final Instant lastModifiedAt, final String value) {
        this.id = id;
        this.customerId = customerId;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.value = value;
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
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public Instant getLastModifiedAt() {
        return lastModifiedAt;
    }

    @Override
    public String getValue() {
        return value;
    }
}
