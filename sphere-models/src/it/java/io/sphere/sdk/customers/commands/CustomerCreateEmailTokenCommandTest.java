package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.Command;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerCreateEmailTokenCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomer(client(), customer -> {
            assertThat(customer.isEmailVerified()).isFalse();
            final int ttlMinutes = 15;
            final Command<CustomerToken> createTokenCommand = CustomerCreateEmailTokenCommand.of(customer, ttlMinutes);

            final CustomerToken customerToken = execute(createTokenCommand);
            final String tokenValue = customerToken.getValue();//this token needs to be sent via email to the customer

            final Command<Customer> verifyEmailCommand = CustomerVerifyEmailCommand.of(customer, tokenValue);
            final Customer loadedCustomer = execute(verifyEmailCommand);
            assertThat(loadedCustomer.isEmailVerified()).isTrue();
        });
    }
}