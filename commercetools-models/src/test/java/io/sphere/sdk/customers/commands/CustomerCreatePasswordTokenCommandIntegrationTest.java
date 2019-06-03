package io.sphere.sdk.customers.commands;

import io.sphere.sdk.customers.CustomerIntegrationTest;
import io.sphere.sdk.customers.CustomerToken;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerCreatePasswordTokenCommandIntegrationTest extends CustomerIntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomer(client(), customer -> {
            final CustomerToken token = client().executeBlocking(CustomerCreatePasswordTokenCommand.of(customer.getEmail(), 20L));
            assertThat(token.getCustomerId()).isEqualTo(customer.getId());
            assertThat(token.getValue().length()).isGreaterThan(0);
            assertThat(token.getId().length()).isGreaterThan(0);
            assertThat(token.getExpiresAt()).isNotNull();
        });
    }
}