package io.sphere.sdk.carts;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartDiscountDraft;
import io.sphere.sdk.cartdiscounts.CartDiscountFixtures;
import io.sphere.sdk.cartdiscounts.CartPredicate;
import io.sphere.sdk.cartdiscounts.commands.CartDiscountCreateCommand;
import io.sphere.sdk.cartdiscounts.commands.CartDiscountDeleteCommand;
import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.carts.commands.CartDeleteCommand;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.AddLineItem;
import io.sphere.sdk.carts.commands.updateactions.SetShippingAddress;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.DiscountCodeDraft;
import io.sphere.sdk.discountcodes.commands.DiscountCodeCreateCommand;
import io.sphere.sdk.discountcodes.commands.DiscountCodeDeleteCommand;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.AddressBuilder;
import io.sphere.sdk.products.Product;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomerAndCart;
import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static io.sphere.sdk.test.SphereTestUtils.DE;
import static io.sphere.sdk.test.SphereTestUtils.EUR;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

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

    public static void withCart(final TestClient client, final Cart cart, final Function<Cart, Cart> f) {
        final Cart updatedCart = f.apply(cart);
        client.execute(CartDeleteCommand.of(updatedCart));
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

    public static void withCartAndDiscountCode(final TestClient client, final BiFunction<Cart, DiscountCode, Cart> user) {
        withCustomerAndCart(client, (customer, cart) -> {
            final CartDiscountDraft draft = CartDiscountFixtures.newCartDiscountDraftBuilder()
                    .cartPredicate(CartPredicate.of(format("customer.id = \"%s\"", customer.getId())))
                    .isActive(true)
                    .validFrom(null)
                    .validUntil(null)
                    .build();
            final CartDiscount cartDiscount = client.execute(CartDiscountCreateCommand.of(draft));
            final DiscountCode discountCode = client.execute(DiscountCodeCreateCommand.of(DiscountCodeDraft.of(randomKey(), cartDiscount)));
            final Cart updatedCart = user.apply(cart, discountCode);
            client.execute(CartDeleteCommand.of(updatedCart));
            client.execute(DiscountCodeDeleteCommand.of(discountCode));
            client.execute(CartDiscountDeleteCommand.of(cartDiscount));
        });
    }
}
