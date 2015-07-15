package io.sphere.sdk.customergroups;

import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.customergroups.commands.CustomerGroupCreateCommand;
import io.sphere.sdk.customergroups.commands.CustomerGroupDeleteCommand;
import io.sphere.sdk.customergroups.queries.CustomerGroupByIdFetch;
import io.sphere.sdk.customergroups.queries.CustomerGroupQuery;

import java.util.Optional;
import java.util.function.Consumer;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class CustomerGroupFixtures {
    public static void withCustomerGroup(final TestClient client, final Consumer<CustomerGroup> consumer) {
        final String name = randomString();
        final CustomerGroup customerGroup = client.execute(CustomerGroupCreateCommand.of(name));
        consumer.accept(customerGroup);
        final Optional<CustomerGroup> customerGroupOptional = Optional.ofNullable(client.execute(CustomerGroupByIdFetch.of(customerGroup.getId())));
        customerGroupOptional.ifPresent(group -> client.execute(CustomerGroupDeleteCommand.of(group)));
    }

    public static void withB2cCustomerGroup(final TestClient client, final Consumer<CustomerGroup> consumer) {
        final CustomerGroup customerGroup = b2cCustomerGroup(client);
        consumer.accept(customerGroup);
    }

    public static CustomerGroup b2cCustomerGroup(final TestClient client) {
        return client.execute(CustomerGroupQuery.of().byName("b2c")).head().orElseGet(() -> client.execute(CustomerGroupCreateCommand.of("b2c")));
    }
}
