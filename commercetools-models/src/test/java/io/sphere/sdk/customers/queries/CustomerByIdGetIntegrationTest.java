package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.queries.Get;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerByIdGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomerInGroup(client(), (customer, customerGroup) -> {
            final Get<Customer> fetch = CustomerByIdGet.of(customer)
                    .withExpansionPaths(CustomerExpansionModel.of().customerGroup());
            final Customer fetchedCustomer = client().executeBlocking(fetch);
            assertThat(fetchedCustomer.getId()).isEqualTo(customer.getId());
            assertThat(fetchedCustomer.getCustomerGroup().getObj().getId()).isEqualTo(customerGroup.getId());
        });
    }
}