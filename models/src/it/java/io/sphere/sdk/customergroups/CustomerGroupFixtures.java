package io.sphere.sdk.customergroups;

import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.customergroups.commands.CustomerGroupCreateCommand;
import io.sphere.sdk.customergroups.commands.CustomerGroupDeleteByIdCommand;
import io.sphere.sdk.customergroups.queries.CustomerGroupFetchById;

import java.util.Optional;
import java.util.function.Consumer;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class CustomerGroupFixtures {
    public static void withCustomerGroup(final TestClient client, final Consumer<CustomerGroup> consumer) {
        final String name = randomString();
        final CustomerGroup customerGroup = client.execute(CustomerGroupCreateCommand.of(name));
        consumer.accept(customerGroup);
        final Optional<CustomerGroup> customerGroupOptional = client.execute(CustomerGroupFetchById.of(customerGroup.getId()));
        customerGroupOptional.ifPresent(group -> client.execute(CustomerGroupDeleteByIdCommand.of(group)));
    }
}
