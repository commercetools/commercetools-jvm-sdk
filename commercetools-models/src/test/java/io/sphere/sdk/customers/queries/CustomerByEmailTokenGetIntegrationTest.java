package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.commands.CustomerCreateEmailTokenCommand;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static org.assertj.core.api.Assertions.assertThat;


public class CustomerByEmailTokenGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomer(client(), customer -> {
            final CustomerToken token = client().executeBlocking(CustomerCreateEmailTokenCommand.of(customer, 5));
            final Customer fetchedCustomer = client().executeBlocking(CustomerByEmailTokenGet.of(token));
            assertThat(fetchedCustomer.getId()).isEqualTo(customer.getId());
        });
    }
}