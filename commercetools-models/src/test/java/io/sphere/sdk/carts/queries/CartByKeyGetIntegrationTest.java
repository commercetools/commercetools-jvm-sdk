package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.CartState;
import io.sphere.sdk.carts.commands.*;
import io.sphere.sdk.carts.commands.updateactions.SetKey;
import io.sphere.sdk.models.DefaultCurrencyUnits;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.carts.CartFixtures.*;
import static io.sphere.sdk.stores.StoreFixtures.withStore;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class CartByKeyGetIntegrationTest extends IntegrationTest {
    
    @Test
    public void fetchByKeyWithReplicateAction()  {
        withCart(client(), cart -> {
            assertThat(cart.getKey()).isNull();
            final String cartKey = randomKey();
            CartReplicationDraft cartReplicationDraft = CartReplicationDraftBuilder.of(cart.toReference()).key(cartKey).build();
            final Cart replicatedCart = client().executeBlocking(CartReplicationCommand.of(cartReplicationDraft));
            assertThat(replicatedCart).isNotNull();
            assertThat(cart.getCartState()).isEqualByComparingTo(CartState.ACTIVE);

            final Cart fetchedCart = client().executeBlocking(CartByKeyGet.of(cartKey));
            assertThat(fetchedCart).isEqualTo(replicatedCart);

            return replicatedCart;
        });
    }

    @Test
    public void fetchByKeyWithUpdateAction()  {
        withCart(client(), cart -> {
            assertThat(cart.getKey()).isNull();
            final String key = randomKey();
            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, SetKey.of(key)));

            assertThat(updatedCart).isNotNull();
            assertThat(updatedCart.getCartState()).isEqualByComparingTo(CartState.ACTIVE);

            final Cart fetchedCart = client().executeBlocking(CartByKeyGet.of(key));
            assertThat(fetchedCart).isEqualTo(updatedCart);

            return updatedCart;
        });
    }

    @Test
    public void fetchCartInStoreByKeyWithReplicateAction() {
        withStore(client(), store -> {
            final CartDraft cartDraft = CartDraft.of(DefaultCurrencyUnits.EUR).withStore(store.toResourceIdentifier());
            withCartDraft(client(), cartDraft, cart -> {
                final String cartKey = randomKey();
                CartReplicationDraft cartReplicationDraft = CartReplicationDraftBuilder.of(cart.toReference()).key(cartKey).build();
                final Cart replicatedCart = client().executeBlocking(CartReplicationCommand.of(cartReplicationDraft));
                assertThat(replicatedCart).isNotNull();

                final Cart cartInStore = client().executeBlocking(CartInStoreByKeyGet.of(store.getKey(), replicatedCart.getKey()));

                assertThat(cartInStore).isNotNull();
                assertThat(cartInStore.getKey()).isEqualTo(replicatedCart.getKey());
                client().executeBlocking(CartDeleteCommand.of(cart));

                return cartInStore;
            });
        });
    }

    @Test
    public void fetchCartInStoreByKeyWithUpdateAction()  {
        withStore(client(), store -> {
            final CartDraft cartDraft = CartDraft.of(DefaultCurrencyUnits.EUR).withStore(store.toResourceIdentifier());
            withCartDraft(client(), cartDraft, cart -> {
                assertThat(cart.getKey()).isNull();
                final String key = randomKey();
                final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, SetKey.of(key)));

                assertThat(updatedCart).isNotNull();
                assertThat(updatedCart.getCartState()).isEqualByComparingTo(CartState.ACTIVE);

                final Cart cartInStore = client().executeBlocking(CartInStoreByKeyGet.of(store.getKey(), updatedCart.getKey()));

                assertThat(cartInStore).isEqualTo(updatedCart);

                return updatedCart;
            });
        });
    }
}
