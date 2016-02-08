package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.client.JsonEndpoint;

final class CustomerGroupEndpoint {
    public static final JsonEndpoint<CustomerGroup> ENDPOINT = JsonEndpoint.of(CustomerGroup.typeReference(), "/customer-groups");
}
