package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.commands.CustomerInStoreCreateEmailTokenCommand;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomerInStore;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerInStoreByEmailTokenGetIntegrationTest extends IntegrationTest {
    
    @Test
    public void execution() throws Exception {
        withCustomerInStore(client(), customer -> {
            final String storeKey = customer.getStores().get(0).getKey();
            final CustomerToken token = client().executeBlocking(CustomerInStoreCreateEmailTokenCommand.of(storeKey, customer, 5));
            final Customer fetchedCustomer = client().executeBlocking(CustomerInStoreByEmailTokenGet.of(storeKey, token));
            assertThat(fetchedCustomer.getId()).isEqualTo(customer.getId());
        });
    }
}
