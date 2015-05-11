package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerByIdFetchTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomer(client(), customer -> {
            final Customer fetchedCustomer = execute(CustomerByIdFetch.of(customer.getId())).get();
            assertThat(fetchedCustomer.getId()).isEqualTo(customer.getId());
        });
    }
}