package io.sphere.sdk.customers.commands;

import io.sphere.sdk.customers.CustomerIntegrationTest;
import io.sphere.sdk.customers.CustomerToken;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomerInStore;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerInStoreCreatePasswordTokenCommandIntegrationTest extends CustomerIntegrationTest {

    @Test
    public void execution() throws Exception {
        withCustomerInStore(client(), customer -> {
            final CustomerToken token = client().executeBlocking(CustomerInStoreCreatePasswordTokenCommand.of(customer.getStores().get(0).getKey(), customer.getEmail(), 20L));
            assertThat(token.getCustomerId()).isEqualTo(customer.getId());
            assertThat(token.getValue().length()).isGreaterThan(0);
            assertThat(token.getId().length()).isGreaterThan(0);
            assertThat(token.getExpiresAt()).isNotNull();
        });
    }
    
}
