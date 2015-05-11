package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.commands.CustomerCreateTokenCommand;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static org.assertj.core.api.Assertions.assertThat;


public class CustomerByTokenFetchTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomer(client(), customer -> {
            final CustomerToken token = execute(CustomerCreateTokenCommand.of(customer.getEmail()));
            final Optional<Customer> fetchedCustomer = execute(CustomerByTokenFetch.of(token));
            assertThat(fetchedCustomer.map(c -> c.getId())).contains(customer.getId());
        });
    }
}