package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.commands.CustomerCreateTokenCommand;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.test.OptionalAssert.assertThat;


public class CustomerFetchByTokenTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomer(client(), customer -> {
            final CustomerToken token = execute(new CustomerCreateTokenCommand(customer.getEmail()));
            final CustomerFetchByToken request = new CustomerFetchByToken(token);
            System.out.println(request);
            final Optional<Customer> fetchedCustomer = execute(request);
            assertThat(fetchedCustomer.map(c -> c.getId())).isPresentAs(customer.getId());
        });
    }
}