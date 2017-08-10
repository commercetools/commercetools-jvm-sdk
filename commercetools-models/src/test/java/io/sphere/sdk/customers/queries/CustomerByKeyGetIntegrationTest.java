package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.commands.CustomerUpdateCommand;
import io.sphere.sdk.customers.commands.updateactions.SetKey;
import io.sphere.sdk.queries.Get;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomerInGroup;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerByKeyGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomerInGroup(client(), (customer, customerGroup) -> {
            final String key = randomKey();
            final Customer customerWithKey = client().executeBlocking(CustomerUpdateCommand.of(customer, SetKey.of(key)));
            final Get<Customer> fetch = CustomerByKeyGet.of(key);
            final Customer fetchedCustomer = client().executeBlocking(fetch);
            assertThat(fetchedCustomer.getId()).isEqualTo(customerWithKey.getId());
        });
    }
}