package io.sphere.sdk.carts;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.AddLineItem;
import io.sphere.sdk.carts.commands.updateactions.SetShippingAddress;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.AddressBuilder;
import io.sphere.sdk.products.Product;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static io.sphere.sdk.test.SphereTestUtils.DE;
import static io.sphere.sdk.test.SphereTestUtils.EUR;
import static org.fest.assertions.Assertions.assertThat;

public class CartFixtures {

    public static final CountryCode DEFAULT_COUNTRY = DE;
    public static final Address GERMAN_ADDRESS = AddressBuilder.of(DEFAULT_COUNTRY).build();

    public static Cart createCart(final TestClient client, final CartDraft cartDraft) {
        return client.execute(CartCreateCommand.of(cartDraft));
    }

    public static Cart createCartWithCountry(final TestClient client) {
        return createCart(client, CartDraft.of(EUR).withCountry(DEFAULT_COUNTRY));
    }

    public static Cart createCartWithoutCountry(final TestClient client) {
        return createCart(client, CartDraft.of(EUR));
    }

    public static Cart createCartWithShippingAddress(final TestClient client) {
        final Cart cart = createCartWithCountry(client);
        return client.execute(CartUpdateCommand.of(cart, SetShippingAddress.of(GERMAN_ADDRESS)));
    }

    public static void withEmptyCartAndProduct(final TestClient client, final BiConsumer<Cart, Product> f) {
        withTaxedProduct(client, product -> {
            final Cart cart = createCartWithCountry(client);
            f.accept(cart, product);
        });
    }

    public static void withFilledCart(final TestClient client, final Consumer<Cart> f) {
        withTaxedProduct(client, product -> {
            final Cart cart = createCartWithShippingAddress(client);
            assertThat(cart.getLineItems()).hasSize(0);
            final long quantity = 3;
            final String productId = product.getId();
            final AddLineItem action = AddLineItem.of(productId, 1, quantity);

            final Cart updatedCart = client.execute(CartUpdateCommand.of(cart, action));
            assertThat(updatedCart.getLineItems()).hasSize(1);
            final LineItem lineItem = updatedCart.getLineItems().get(0);
            assertThat(lineItem.getName()).isEqualTo(product.getMasterData().getStaged().getName());
            assertThat(lineItem.getQuantity()).isEqualTo(quantity);
            f.accept(updatedCart);
        });
    }
}
