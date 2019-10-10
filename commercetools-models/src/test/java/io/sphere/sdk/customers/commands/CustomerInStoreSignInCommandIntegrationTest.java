package io.sphere.sdk.customers.commands;

import io.sphere.sdk.customers.CustomerIntegrationTest;
import io.sphere.sdk.customers.CustomerSignInResult;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.PASSWORD;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomerInStore;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerInStoreSignInCommandIntegrationTest extends CustomerIntegrationTest {

    @Test
    public void execution() throws Exception {
        withCustomerInStore(client(), customer -> {
            final CustomerSignInResult result = client().executeBlocking(CustomerInStoreSignInCommand.of(customer.getStores().get(0).getKey(), customer.getEmail(), PASSWORD));
             assertThat(result.getCustomer()).isEqualTo(customer);
        });
    }
    
}
