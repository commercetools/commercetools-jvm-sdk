package io.sphere.sdk.customers.commands;

import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerCreatePasswordTokenCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomer(client(), customer -> {
            final CustomerToken token = execute(CustomerCreatePasswordTokenCommand.of(customer.getEmail()));
            assertThat(token.getCustomerId()).isEqualTo(customer.getId());
            assertThat(token.getValue().length()).isGreaterThan(0);
            assertThat(token.getId().length()).isGreaterThan(0);
        });
    }
}