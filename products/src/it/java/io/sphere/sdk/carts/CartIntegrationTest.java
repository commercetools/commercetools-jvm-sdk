package io.sphere.sdk.carts;

import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.carts.queries.FetchCartById;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static com.neovisionaries.i18n.CountryCode.DE;
import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.test.OptionalAssert.assertThat;
import static org.fest.assertions.Assertions.assertThat;

public class CartIntegrationTest extends IntegrationTest {

    @Test
    public void create() throws Exception {
        final CartDraft cartDraft = CartDraft.of(EUR).withCountry(DE);
        final Cart cart = execute(new CartCreateCommand(cartDraft));
        assertThat(cart.getTotalPrice().getCurrency()).isEqualTo(EUR);
        assertThat(cart.getCountry()).isEqualTo(Optional.of(DE));
        assertThat(cart.getTotalPrice().isZero()).isTrue();
    }

    @Test
    public void fetchById() throws Exception {
        final Cart cart = createCartSomeHow();
        final Optional<Cart> fetchedCartOptional = execute(new FetchCartById(cart));
        assertThat(fetchedCartOptional).isPresentAs(cart);
    }

    private Cart createCartSomeHow() {
        final CartDraft cartDraft = CartDraft.of(EUR).withCountry(DE);
        return execute(new CartCreateCommand(cartDraft));
    }
}
