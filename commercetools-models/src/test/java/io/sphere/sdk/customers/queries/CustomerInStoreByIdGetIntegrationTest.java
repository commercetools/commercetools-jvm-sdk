package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerFixtures;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerInStoreByIdGetIntegrationTest extends IntegrationTest {
    
    @Test
    public void execute() throws Exception {
        CustomerFixtures.withCustomerInStore(client(), customer -> {
            final String storeKey = customer.getStores().get(0).getKey();
            final Customer queriedCustomer = client().executeBlocking(CustomerInStoreByIdGet.of(storeKey, customer.getId()));
            assertThat(queriedCustomer).isNotNull();
            assertThat(queriedCustomer.getId()).isEqualTo(customer.getId());
        });
    }
    
}
