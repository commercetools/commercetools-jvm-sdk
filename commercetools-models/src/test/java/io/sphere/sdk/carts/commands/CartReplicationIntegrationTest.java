package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartState;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.carts.CartFixtures.withCart;
import static org.assertj.core.api.Assertions.assertThat;
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
    public void replicateCartFromOrder(){
        withOrder(client(),order -> {
            CartReplicationDraft cartReplicationDraft = CartReplicationDraftBuilder.of(order.toReference()).build();
            final Cart replicatedCart = client().executeBlocking(CartReplicationCommand.of(cartReplicationDraft));
            assertThat(replicatedCart).isNotNull();
            assertThat(replicatedCart.getCartState()).isEqualByComparingTo(CartState.ACTIVE);
            return order;
        });
    }

}
