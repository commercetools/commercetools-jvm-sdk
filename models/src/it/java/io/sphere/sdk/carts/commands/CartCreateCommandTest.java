package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.OptionalAssert;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.fest.assertions.Assertions.assertThat;

public class CartCreateCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final CartDraft cartDraft = CartDraft.of(EUR).withCountry(DE);
        final Cart cart = execute(new CartCreateCommand(cartDraft));
        assertThat(cart.getTotalPrice().getCurrency().getCurrencyCode()).isEqualTo(EUR.getCurrencyCode());
        OptionalAssert.assertThat(cart.getCountry()).isEqualTo(Optional.of(DE));
        assertThat(cart.getTotalPrice().isZero()).isTrue();
    }
}