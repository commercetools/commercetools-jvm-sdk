package io.sphere.sdk.customergroups.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customergroups.CustomerGroupFixtures.withCustomerGroup;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerGroupByIdGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() {
        withCustomerGroup(client(), customerGroup -> {
            final String customerGroupId = customerGroup.getId();

            final CustomerGroup fetchedCustomerGroup = client().executeBlocking(CustomerGroupByIdGet.of(customerGroupId));

            assertThat(fetchedCustomerGroup).isEqualTo(customerGroup);
        });
    }
}