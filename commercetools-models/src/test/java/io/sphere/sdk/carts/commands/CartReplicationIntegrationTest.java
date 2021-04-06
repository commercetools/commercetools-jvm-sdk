package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartFixtures;
import io.sphere.sdk.carts.CartState;
import io.sphere.sdk.stores.StoreFixtures;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.carts.CartFixtures.withCart;
import static io.sphere.sdk.carts.CartFixtures.withFilledCartInStore;
import static org.assertj.core.api.Assertions.assertThat;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static io.sphere.sdk.orders.OrderFixtures.*;

public class CartReplicationIntegrationTest extends IntegrationTest {


    @Test
    public void replicateCartFromCart(){
        withCart(client(),cart -> {
            CartReplicationDraft cartReplicationDraft = CartReplicationDraftBuilder.of(cart.toReference()).build();
            final Cart replicatedCart = client().executeBlocking(CartReplicationCommand.of(cartReplicationDraft));
            assertThat(replicatedCart).isNotNull();
            assertThat(cart.getCartState()).isEqualByComparingTo(CartState.ACTIVE);
           return replicatedCart;
        });
    }

    @Test
    public void replicateCartFromCartWithKey(){
        withCart(client(), cart -> {
            String cartKey = randomKey();
            CartReplicationDraft cartReplicationDraft = CartReplicationDraftBuilder.of(cart.toReference()).key(cartKey).build();
            final Cart replicatedCart = client().executeBlocking(CartReplicationCommand.of(cartReplicationDraft));
            assertThat(replicatedCart).isNotNull();
            assertThat(cart.getCartState()).isEqualByComparingTo(CartState.ACTIVE);
            return replicatedCart;
        });
    }


    @Test
    public void replicateCartFromOrder(){
        withOrder(client(),order -> {
            CartReplicationDraft cartReplicationDraft = CartReplicationDraftBuilder.of(order.toReference()).build();
            final Cart replicatedCart = client().executeBlocking(CartReplicationCommand.of(cartReplicationDraft));
            assertThat(replicatedCart).isNotNull();
            assertThat(replicatedCart.getCartState()).isEqualByComparingTo(CartState.ACTIVE);
            return order;
        });
    }

    @Test
    public void inStoreReplicateCartFromCart(){
        StoreFixtures.withStore(client(), store -> {
            withFilledCartInStore(client(), store, cart -> {
                CartReplicationDraft cartReplicationDraft = CartReplicationDraftBuilder.of(cart.toReference()).build();
                final Cart replicatedCart = client().executeBlocking(CartReplicationCommand.of(cartReplicationDraft));
                assertThat(replicatedCart).isNotNull();
                assertThat(cart.getCartState()).isEqualByComparingTo(CartState.ACTIVE);
                assertThat(cart.getStore().getKey()).isEqualTo(store.getKey());
                assertThat(cart.getStore().getKey()).isEqualTo(replicatedCart.getStore().getKey());

                client().executeBlocking(CartDeleteCommand.of(replicatedCart));
            });
        });
    }

}
