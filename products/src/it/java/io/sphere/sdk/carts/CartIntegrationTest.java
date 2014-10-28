package io.sphere.sdk.carts;

import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.AddLineItem;
import io.sphere.sdk.carts.commands.updateactions.ChangeLineItemQuantity;
import io.sphere.sdk.carts.commands.updateactions.RemoveLineItem;
import io.sphere.sdk.carts.queries.FetchCartById;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;
import java.util.function.BiConsumer;

import static com.neovisionaries.i18n.CountryCode.DE;
import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static io.sphere.sdk.test.OptionalAssert.assertThat;
import static org.fest.assertions.Assertions.assertThat;

public class CartIntegrationTest extends IntegrationTest {

    public static final int MASTER_VARIANT_ID = 1;

    @Test
    public void create() throws Exception {
        final CartDraft cartDraft = CartDraft.of(EUR).withCountry(DE);
        final Cart cart = execute(new CartCreateCommand(cartDraft));
        assertThat(cart.getTotalPrice().getCurrency().getCurrencyCode()).isEqualTo(EUR.getCurrencyCode());
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

    @Test
    public void removeLineItemUpdateAction() throws Exception {
        withEmptyCartAndProduct((cart, product) -> {
            assertThat(cart.getLineItems()).hasSize(0);
            final AddLineItem action = AddLineItem.of(product.getId(), MASTER_VARIANT_ID, 3);
            final Cart cartWith3 = execute(new CartUpdateCommand(cart, action));
            final LineItem lineItem = cartWith3.getLineItems().get(0);
            assertThat(lineItem.getQuantity()).isEqualTo(3);
            final Cart cartWith2 = execute(new CartUpdateCommand(cartWith3, RemoveLineItem.of(lineItem, 1)));
            assertThat(cartWith2.getLineItems().get(0).getQuantity()).isEqualTo(2);
            final Cart cartWith0 = execute(new CartUpdateCommand(cartWith2, RemoveLineItem.of(lineItem)));
            assertThat(cartWith0.getLineItems()).hasSize(0);
        });
    }

    @Test
    public void changeLineItemQuantityUpdateAction() throws Exception {
        withEmptyCartAndProduct((cart, product) -> {
            assertThat(cart.getLineItems()).hasSize(0);
            final AddLineItem action = AddLineItem.of(product.getId(), MASTER_VARIANT_ID, 3);
            final Cart cartWith3 = execute(new CartUpdateCommand(cart, action));
            final LineItem lineItem = cartWith3.getLineItems().get(0);
            assertThat(lineItem.getQuantity()).isEqualTo(3);
            final Cart cartWith2 = execute(new CartUpdateCommand(cartWith3, ChangeLineItemQuantity.of(lineItem, 2)));
            assertThat(cartWith2.getLineItems().get(0).getQuantity()).isEqualTo(2);
            final Cart cartWith0 = execute(new CartUpdateCommand(cartWith2, ChangeLineItemQuantity.of(lineItem, 0)));
            assertThat(cartWith0.getLineItems()).hasSize(0);
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
