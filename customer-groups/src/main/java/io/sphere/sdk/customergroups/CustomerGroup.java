package io.sphere.sdk.customergroups;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = CustomerGroupImpl.class)
public interface CustomerGroup {
}
