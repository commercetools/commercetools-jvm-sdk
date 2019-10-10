package io.sphere.sdk.customers.commands;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerIntegrationTest;
import io.sphere.sdk.customers.commands.updateactions.SetFirstName;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withUpdateableCustomerInStore;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerInStoreByKeyUpdateCommandIntegrationTest extends CustomerIntegrationTest {
    
    @Test
    public void setFirstName() {
        withUpdateableCustomerInStore(client(), customer -> {
            final String storeKey = customer.getStores().get(0).getKey();
            final String newFirstName = "Jane";
            assertThat(customer.getFirstName()).isNotEqualTo(newFirstName);
            final Customer updatedCustomer = client().executeBlocking(CustomerInStoreUpdateCommand.ofKey(customer.getKey(), customer.getVersion(), storeKey, SetFirstName.of(newFirstName)));
            assertThat(updatedCustomer.getFirstName()).isEqualTo(newFirstName);
            return updatedCustomer;
        });
    }
}