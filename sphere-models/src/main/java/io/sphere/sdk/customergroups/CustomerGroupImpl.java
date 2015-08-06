package io.sphere.sdk.customergroups;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.ResourceImpl;

import java.time.ZonedDateTime;

final class CustomerGroupImpl extends ResourceImpl<CustomerGroup> implements CustomerGroup {
    private final String name;

    @JsonCreator
    CustomerGroupImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final String name) {
        super(id, version, createdAt, lastModifiedAt);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
