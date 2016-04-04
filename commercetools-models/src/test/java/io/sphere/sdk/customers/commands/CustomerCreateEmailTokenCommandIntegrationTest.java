package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.Command;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerIntegrationTest;
import io.sphere.sdk.customers.CustomerToken;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerCreateEmailTokenCommandIntegrationTest extends CustomerIntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomer(client(), customer -> {
            assertThat(customer.isEmailVerified()).isFalse();
            final int ttlMinutes = 15;
            final Command<CustomerToken> createTokenCommand = CustomerCreateEmailTokenCommand.of(customer, ttlMinutes);

            final CustomerToken customerToken = client().executeBlocking(createTokenCommand);
            final String tokenValue = customerToken.getValue();//this token needs to be sent via email to the customer

            final Command<Customer> verifyEmailCommand = CustomerVerifyEmailCommand.ofTokenValue(tokenValue);
            final Customer loadedCustomer = client().executeBlocking(verifyEmailCommand);
            assertThat(loadedCustomer.isEmailVerified()).isTrue();
        });
    }
}