package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.*;
import static org.fest.assertions.Assertions.assertThat;

public class CustomerFetchByIdTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomer(client(), customer -> {
            final Customer fetchedCustomer = execute(CustomerFetchById.of(customer.getId())).get();
            assertThat(fetchedCustomer.getId()).isEqualTo(customer.getId());
        });
    }
}