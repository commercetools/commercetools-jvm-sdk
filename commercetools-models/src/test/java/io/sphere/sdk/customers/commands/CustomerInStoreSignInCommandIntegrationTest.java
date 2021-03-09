package io.sphere.sdk.customers.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.carts.commands.CartDeleteCommand;
import io.sphere.sdk.customers.CustomerFixtures;
import io.sphere.sdk.customers.CustomerIntegrationTest;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.stores.Store;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.*;
import static io.sphere.sdk.stores.StoreFixtures.withStore;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerInStoreSignInCommandIntegrationTest extends CustomerIntegrationTest {

    @Test
    public void execution() throws Exception {
        withCustomerInStore(client(), customer -> {
            final CustomerSignInResult result = client().executeBlocking(CustomerInStoreSignInCommand.of(customer.getStores().get(0).getKey(), customer.getEmail(), PASSWORD));
             assertThat(result.getCustomer()).isEqualTo(customer);
        });
    }

    @Test
    public void anonymousCartInStore() throws Exception {
        withCustomerInStore(client(), customer -> {
            final String anonymousId = randomKey();
            final ResourceIdentifier<Store> storeResourceIdentifier = ResourceIdentifier.ofKey(customer.getStores().get(0).getKey(), "store");
            final CartDraft cartDraft = CartDraft.of(EUR).withCountry(DE).withAnonymousId(anonymousId).withStore(storeResourceIdentifier);
            final Cart cart = client().executeBlocking(CartCreateCommand.of(cartDraft));
            final CustomerInStoreSignInCommand customerInStoreSignInCommand =
                    CustomerInStoreSignInCommand.of(customer.getStores().get(0).getKey(), customer.getEmail(), PASSWORD)
                    .withAnonymousCart(customer.getStores().get(0).getKey(), cart.toResourceIdentifier());
            final CustomerSignInResult customerSignInResult = client().executeBlocking(customerInStoreSignInCommand);

            assertThat(customerSignInResult.getCart().getId())
                    .as("the customer gets the cart from the anonymous session assigned while on sign-in")
                    .isEqualTo(cart.getId());
            assertThat(customerInStoreSignInCommand.getAnonymousCart()).isEqualTo(cart.toResourceIdentifier());
        });
    }
    
}
