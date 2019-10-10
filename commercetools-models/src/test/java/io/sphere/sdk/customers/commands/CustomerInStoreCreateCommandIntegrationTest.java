package io.sphere.sdk.customers.commands;

import io.sphere.sdk.customers.*;
import io.sphere.sdk.stores.StoreFixtures;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerInStoreCreateCommandIntegrationTest extends CustomerIntegrationTest {
    
    @Test
    public void createCustomerWithCart() throws Exception {
        StoreFixtures.withStore(client(), store -> {
            final CustomerDraft customerDraft = CustomerFixtures.newCustomerDraft().withKey(randomKey());
            final CustomerSignInResult signInResult = client().executeBlocking(CustomerInStoreCreateCommand.of(store.getKey(), customerDraft));
            final Customer customer = signInResult.getCustomer();
            assertThat(customer).isNotNull();
            final Customer deletedCustomer = client().executeBlocking(CustomerDeleteCommand.of(customer));
            assertThat(deletedCustomer).isNotNull();
        });
    }
}