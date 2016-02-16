package io.sphere.sdk.customergroups;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.customergroups.commands.CustomerGroupCreateCommand;
import io.sphere.sdk.customergroups.commands.CustomerGroupDeleteCommand;
import io.sphere.sdk.customergroups.queries.CustomerGroupByIdGet;
import io.sphere.sdk.customergroups.queries.CustomerGroupQuery;

import java.util.Optional;
import java.util.function.Consumer;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class CustomerGroupFixtures {
    public static void withCustomerGroup(final BlockingSphereClient client, final Consumer<CustomerGroup> consumer) {
        final String name = randomString();
        final CustomerGroup customerGroup = client.executeBlocking(CustomerGroupCreateCommand.of(name));
        consumer.accept(customerGroup);
        final Optional<CustomerGroup> customerGroupOptional = Optional.ofNullable(client.executeBlocking(CustomerGroupByIdGet.of(customerGroup.getId())));
        customerGroupOptional.ifPresent(group -> client.executeBlocking(CustomerGroupDeleteCommand.of(group)));
    }

    public static void withB2cCustomerGroup(final BlockingSphereClient client, final Consumer<CustomerGroup> consumer) {
        final CustomerGroup customerGroup = b2cCustomerGroup(client);
        consumer.accept(customerGroup);
    }

    public static CustomerGroup b2cCustomerGroup(final BlockingSphereClient client) {
        return client.executeBlocking(CustomerGroupQuery.of().byName("b2c")).head().orElseGet(() -> client.executeBlocking(CustomerGroupCreateCommand.of("b2c")));
    }

    public static void withCustomerGroup(final BlockingSphereClient client, final CustomerGroupDraft draft, final Consumer<CustomerGroup> consumer) {
        final CustomerGroup customerGroup = client.executeBlocking(CustomerGroupCreateCommand.of(draft));
        consumer.accept(customerGroup);
        client.executeBlocking(CustomerGroupDeleteCommand.of(customerGroup));
    }
}
