package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.commands.updateactions.ChangeName;
import io.sphere.sdk.customergroups.commands.updateactions.SetKey;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;
import static io.sphere.sdk.test.SphereTestUtils.*;

import static io.sphere.sdk.customergroups.CustomerGroupFixtures.withCustomerGroup;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerGroupUpdateCommandIntegrationTest extends IntegrationTest {
    @Test
    public void changeName() throws Exception {
        withCustomerGroup(client(), customerGroup -> {
            final String newName = randomString();
            assertThat(customerGroup.getName()).isNotEqualTo(newName);
            final CustomerGroup updatedCustomerGroup = client().executeBlocking(CustomerGroupUpdateCommand.of(customerGroup, ChangeName.of(newName)));
            assertThat(updatedCustomerGroup.getName()).isEqualTo(newName);
        });
    }

    @Test
    public void setKey() throws Exception {
        withCustomerGroup(client(), customerGroup -> {
            final String newKey = randomKey();
            assertThat(customerGroup.getKey()).isNotEqualTo(newKey);
            final CustomerGroup updatedCustomerGroup = client().executeBlocking(CustomerGroupUpdateCommand.of(customerGroup, SetKey.of(newKey)));
            assertThat(updatedCustomerGroup.getKey()).isEqualTo(newKey);
        });
    }
}