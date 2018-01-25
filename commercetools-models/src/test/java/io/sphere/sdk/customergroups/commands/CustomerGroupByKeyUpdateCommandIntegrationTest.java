package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.commands.updateactions.ChangeName;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.*;

import static io.sphere.sdk.customergroups.CustomerGroupFixtures.withCustomerGroup;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerGroupByKeyUpdateCommandIntegrationTest extends IntegrationTest {
    @Test
    public void changeName() throws Exception {
        withCustomerGroup(client(), customerGroup -> {
            final String newName = randomString();
            assertThat(customerGroup.getName()).isNotEqualTo(newName);
            final CustomerGroup updatedCustomerGroup = client().executeBlocking(CustomerGroupUpdateCommand.ofKey(customerGroup.getKey(), customerGroup.getVersion(), ChangeName.of(newName)));
            assertThat(updatedCustomerGroup.getName()).isEqualTo(newName);
            return updatedCustomerGroup;
        });
    }

}