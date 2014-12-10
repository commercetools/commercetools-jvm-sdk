package io.sphere.sdk.customergroups;

import io.sphere.sdk.models.DefaultModelImpl;

import java.time.Instant;

final class CustomerGroupImpl extends DefaultModelImpl<CustomerGroup> implements CustomerGroup {
    private final String name;

    CustomerGroupImpl(final String id, final long version, final Instant createdAt, final Instant lastModifiedAt, final String name) {
        super(id, version, createdAt, lastModifiedAt);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
