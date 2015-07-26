package io.sphere.sdk.customers.commands;

import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.customers.CustomerInvalidCredentials;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.PASSWORD;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CustomerSignInCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomer(client(), customer -> {
            final CustomerSignInResult result = execute(CustomerSignInCommand.of(customer.getEmail(), PASSWORD));
            assertThat(result.getCustomer()).isEqualTo(customer);
        });
    }

    @Test
    public void executionWithInvalidEmail() throws Exception {
        withCustomer(client(), customer -> {
            assertThatThrownBy(() -> execute(CustomerSignInCommand.of("notpresent@null.sphere.io", PASSWORD)))
                    .isInstanceOf(ErrorResponseException.class)
                    .matches(e -> ((ErrorResponseException) e).hasErrorCode(CustomerInvalidCredentials.CODE));
        });
    }
}