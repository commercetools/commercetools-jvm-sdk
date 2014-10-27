package io.sphere.sdk.carts;

import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.AddLineItem;
import io.sphere.sdk.carts.queries.FetchCartById;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;
import java.util.function.BiConsumer;

import static com.neovisionaries.i18n.CountryCode.DE;
import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static io.sphere.sdk.test.OptionalAssert.assertThat;
import static org.fest.assertions.Assertions.assertThat;

public class CartIntegrationTest extends IntegrationTest {

    public static final int MASTER_VARIANT_ID = 1;

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

    @Test
    public void addLineItemUpdateAction() throws Exception {
        withEmptyCartAndProduct((cart, product) -> {
            assertThat(cart.getLineItems()).hasSize(0);
            final int quantity = 3;
            final String productId = product.getId();
            final AddLineItem action = AddLineItem.of(productId, MASTER_VARIANT_ID, quantity);
            final Cart updatedCart = execute(new CartUpdateCommand(cart, action));
            assertThat(updatedCart.getLineItems()).hasSize(1);
            final LineItem lineItem = updatedCart.getLineItems().get(0);
            assertThat(lineItem.getName()).isEqualTo(product.getMasterData().getStaged().getName());
            assertThat(lineItem.getQuantity()).isEqualTo(quantity);
        });
    }

    private void withEmptyCartAndProduct(final BiConsumer<Cart, Product> f) {
        withTaxedProduct(client(), product -> {
            final Cart cart = createCartSomeHow();
            f.accept(cart, product);
        });
    }

    private Cart createCartSomeHow() {
        final CartDraft cartDraft = CartDraft.of(EUR).withCountry(DE);
        return execute(new CartCreateCommand(cartDraft));
    }
}
