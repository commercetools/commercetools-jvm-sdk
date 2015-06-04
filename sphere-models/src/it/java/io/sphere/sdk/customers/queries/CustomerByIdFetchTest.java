package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.queries.Fetch;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerByIdFetchTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomerInGroup(client(), (customer, customerGroup) -> {
            final Fetch<Customer> fetch = CustomerByIdFetch.of(customer)
                    .withExpansionPath(CustomerExpansionModel.of().customerGroup());
            final Customer fetchedCustomer = execute(fetch).get();
            assertThat(fetchedCustomer.getId()).isEqualTo(customer.getId());
            assertThat(fetchedCustomer.getCustomerGroup().get().getObj().get().getId()).isEqualTo(customerGroup.getId());
        });
    }
}