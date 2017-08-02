package io.sphere.sdk.customergroups.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customergroups.CustomerGroupFixtures.withCustomerGroup;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerGroupByKeyGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() {
        withCustomerGroup(client(), customerGroup -> {
            final String customerGroupKey = customerGroup.getKey();
            assertThat(customerGroupKey).isNotEmpty();
            final CustomerGroup fetchedCustomerGroup = client().executeBlocking(CustomerGroupByKeyGet.of(customerGroupKey));
            assertThat(fetchedCustomerGroup).isEqualTo(customerGroup);
        });
    }
}