package io.sphere.sdk.customers.commands;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static org.fest.assertions.Assertions.assertThat;

public class CustomerPasswordResetCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomer(client(), customer -> {
            final CustomerToken token = execute(CustomerCreateTokenCommand.of(customer.getEmail()));
            final String newPassword = "newPassword";
            final Customer updatedCustomer = execute(CustomerPasswordResetCommand.of(customer, token, newPassword));

            final CustomerSignInResult signInResult = execute(CustomerSignInCommand.of(updatedCustomer.getEmail(), newPassword, Optional.empty()));
            assertThat(signInResult.getCustomer().getId())
                    .overridingErrorMessage("customer can sign in with the new password")
                    .isEqualTo(customer.getId());
        });
    }
}