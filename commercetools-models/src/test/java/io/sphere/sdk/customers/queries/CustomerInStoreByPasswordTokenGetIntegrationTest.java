package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.commands.CustomerInStoreCreatePasswordTokenCommand;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomerInStore;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerInStoreByPasswordTokenGetIntegrationTest extends IntegrationTest {

    @Test
    public void execution() throws Exception {
        withCustomerInStore(client(), customer -> {
            final String storeKey = customer.getStores().get(0).getKey();
            final CustomerToken token = client().executeBlocking(CustomerInStoreCreatePasswordTokenCommand.of(storeKey, customer.getEmail()));
            final Customer fetchedCustomer = client().executeBlocking(CustomerInStoreByPasswordTokenGet.of(storeKey, token));
            assertThat(fetchedCustomer.getId()).isEqualTo(customer.getId());
        });
    }
}