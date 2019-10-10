package io.sphere.sdk.customers.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.queries.CartByCustomerIdGet;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerDraft;
import io.sphere.sdk.customers.CustomerIntegrationTest;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.stores.Store;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.sphere.sdk.customers.CustomerFixtures.newCustomerDraft;
import static io.sphere.sdk.stores.StoreFixtures.withStore;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerInStoreDeleteCommandIntegrationTest extends CustomerIntegrationTest {
    
    @Test
    public void deleteById() throws Exception {
        withStore(client(), store -> {
            final List<ResourceIdentifier<Store>> stores = new ArrayList<>();
            stores.add(ResourceIdentifier.ofId(store.getId()));
            final CustomerDraft customerDraft = newCustomerDraft().withKey(randomKey()).withStores(stores);
            final CustomerSignInResult signInResult = client().executeBlocking(CustomerCreateCommand.of(customerDraft));
            final Customer customer = signInResult.getCustomer();

            client().executeBlocking(CustomerInStoreDeleteCommand.of(store.getKey(), customer));
            final Cart cart = client().executeBlocking(CartByCustomerIdGet.of(customer));
            assertThat(cart).isNull();
        });
    }

    @Test
    public void deleteByKey() throws Exception {
        withStore(client(), store -> {
            final List<ResourceIdentifier<Store>> stores = new ArrayList<>();
            stores.add(ResourceIdentifier.ofId(store.getId()));
            final CustomerDraft customerDraft = newCustomerDraft().withKey(randomKey()).withStores(stores);
            final CustomerSignInResult signInResult = client().executeBlocking(CustomerCreateCommand.of(customerDraft));
            final Customer customer = signInResult.getCustomer();

            client().executeBlocking(CustomerInStoreDeleteCommand.ofKey(store.getKey(), customer.getKey(), customer.getVersion()));
            final Cart cart = client().executeBlocking(CartByCustomerIdGet.of(customer));
            assertThat(cart).isNull();
        });
    }
    
}