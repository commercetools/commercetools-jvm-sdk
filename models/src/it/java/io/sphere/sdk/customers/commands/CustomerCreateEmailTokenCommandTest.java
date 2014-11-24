package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.Command;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static org.fest.assertions.Assertions.assertThat;

public class CustomerCreateEmailTokenCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomer(client(), customer -> {
            assertThat(customer.isEmailVerified()).isFalse();
            final int ttlMinutes = 15;
            final Command<CustomerToken> command1 = CustomerCreateEmailTokenCommand.of(customer, ttlMinutes);
            final CustomerToken customerToken = execute(command1);
            final Command<Customer> command2 = CustomerVerifyEmailCommand.of(customer, customerToken);
            final Customer loadedCustomer = execute(command2);
            assertThat(loadedCustomer.isEmailVerified()).isTrue();
        });
    }
}