package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.InventoryMode;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CartCreateCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final CartDraft cartDraft = CartDraft.of(EUR).withCountry(DE);
        final Cart cart = execute(CartCreateCommand.of(cartDraft));
        assertThat(cart.getTotalPrice().getCurrency().getCurrencyCode()).isEqualTo(EUR.getCurrencyCode());
        assertThat(cart.getCountry()).isEqualTo(DE);
        assertThat(cart.getTotalPrice().isZero()).isTrue();
    }

    @Test
    public void inventoryModeNone() {
        testInventoryMode(InventoryMode.NONE);
    }

    @Test
    public void inventoryModeReserveOnOrder() {
        testInventoryMode(InventoryMode.RESERVE_ON_ORDER);
    }

    @Test
    public void inventoryModeTrackOnly() {
        testInventoryMode(InventoryMode.TRACK_ONLY);
    }

    private void testInventoryMode(final InventoryMode inventoryMode) {
        final Cart cart = execute(CartCreateCommand.of(CartDraft.of(EUR).withInventoryMode(inventoryMode)));
        assertThat(cart.getInventoryMode()).isEqualTo(inventoryMode);
        execute(CartDeleteCommand.of(cart));
    }
}