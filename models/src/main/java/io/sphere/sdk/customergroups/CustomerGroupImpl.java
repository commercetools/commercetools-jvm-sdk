package io.sphere.sdk.customergroups;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.DefaultModelImpl;

import java.time.Instant;

final class CustomerGroupImpl extends DefaultModelImpl<CustomerGroup> implements CustomerGroup {
    private final String name;

    @JsonCreator
    CustomerGroupImpl(final String id, final long version, final Instant createdAt, final Instant lastModifiedAt, final String name) {
        super(id, version, createdAt, lastModifiedAt);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
