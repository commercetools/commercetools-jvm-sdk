package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.carts.commands.CartDeleteCommand;
import io.sphere.sdk.customers.CustomerFixtures;
import io.sphere.sdk.models.DefaultCurrencyUnits;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.carts.CartFixtures.createCartWithCountry;
import static io.sphere.sdk.stores.StoreFixtures.withStore;
import static org.assertj.core.api.Assertions.assertThat;

public class CartByIdGetIntegrationTest extends IntegrationTest {
    
    @Test
    public void fetchById() throws Exception {
        final Cart cart = createCartWithCountry(client());
        final String id = cart.getId();
        final Cart fetchedCart = client().executeBlocking(CartByIdGet.of(id));
        assertThat(fetchedCart).isEqualTo(cart);
    }

    @Test
    public void fetchCartInStoreById() {
        withStore(client(), store -> {
            final CartDraft cartDraft = CartDraft.of(DefaultCurrencyUnits.EUR).withStore(store.toResourceIdentifier());
            final CartCreateCommand cartCreateCommand = CartCreateCommand.of(cartDraft);
            final Cart cart = client().executeBlocking(cartCreateCommand);
            final Cart cartInStore = client().executeBlocking(CartInStoreByIdGet.of(store.getKey(), cart.getId()));
            assertThat(cartInStore).isNotNull();
            assertThat(cartInStore.getId()).isEqualTo(cart.getId());
            client().executeBlocking(CartDeleteCommand.of(cart));
        });
    }
    
    @Test
    public void fetchCartInStoreByCustomerId() {
        
        withStore(client(), store -> { 
            CustomerFixtures.withCustomer(client(), customer -> {
                final CartDraft cartDraft = CartDraft.of(DefaultCurrencyUnits.EUR).withStore(store.toResourceIdentifier()).withCustomerId(customer.getId());
                final CartCreateCommand cartCreateCommand = CartCreateCommand.of(cartDraft);
                final Cart cart = client().executeBlocking(cartCreateCommand);
                
                final Cart cartInStore = client().executeBlocking(CartInStoreByCustomerIdGet.of(store.getKey(), customer.getId()));
                assertThat(cartInStore).isNotNull();
                assertThat(cartInStore.getId()).isEqualTo(cart.getId());
                assertThat(cartInStore.getCustomerId()).isEqualTo(customer.getId());
                client().executeBlocking(CartDeleteCommand.of(cart));
            });
        });
    }
    
}