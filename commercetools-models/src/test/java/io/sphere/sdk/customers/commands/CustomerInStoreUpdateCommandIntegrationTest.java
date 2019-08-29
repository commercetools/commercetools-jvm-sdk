package io.sphere.sdk.customers.commands;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerIntegrationTest;
import io.sphere.sdk.customers.commands.updateactions.SetKey;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withUpdateableCustomerInStore;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerInStoreUpdateCommandIntegrationTest extends CustomerIntegrationTest {
    
    @Test
    public void setKey() {
        withUpdateableCustomerInStore(client(), customer -> {
            final String storeKey = customer.getStores().get(0).getKey();
            final String newKey = randomKey();
            assertThat(customer.getKey()).isNotEqualTo(newKey);
            final Customer updatedCustomer = client().executeBlocking(CustomerInStoreUpdateCommand.of(customer, storeKey, SetKey.of(newKey)));
            assertThat(updatedCustomer.getKey()).isEqualTo(newKey);
            
            return updatedCustomer;
        });
    }
    
}
