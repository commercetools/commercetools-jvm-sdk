package io.sphere.sdk.customers.commands;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.customers.InvalidCurrentPasswordException;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.customers.CustomerFixtures.PASSWORD;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

public class CustomerChangePasswordCommandTest extends IntegrationTest {

    @Test
    public void execution() throws Exception {
        withCustomer(client(), customer -> {
            final String oldPassword = PASSWORD;
            final String newPassword = "newSecret";
            final Customer updatedCustomer = execute(CustomerChangePasswordCommand.of(customer, oldPassword, newPassword));
            assertThat(customer.getPassword()).isNotEqualTo(updatedCustomer.getPassword());
            final CustomerSignInResult signInResult =
                    execute(CustomerSignInCommand.of(customer.getEmail(), newPassword, Optional.empty()));
            assertThat(signInResult.getCustomer().hasSameIdAs(updatedCustomer))
                    .overridingErrorMessage("sign in works with new password")
                    .isTrue();
            try {
                execute(CustomerSignInCommand.of(customer.getEmail(), oldPassword, Optional.empty()));
                fail();
            } catch (final Exception e) {
                final boolean causeIsOk = e.getCause() instanceof InvalidCurrentPasswordException || e instanceof InvalidCurrentPasswordException;
                if (!causeIsOk) {
                    throw e;
                }
            }
        });
    }
}