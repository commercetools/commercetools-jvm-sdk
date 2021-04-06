package io.sphere.sdk.carts;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.cartdiscounts.*;
import io.sphere.sdk.cartdiscounts.commands.CartDiscountCreateCommand;
import io.sphere.sdk.cartdiscounts.commands.CartDiscountDeleteCommand;
import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.carts.commands.CartDeleteCommand;
import io.sphere.sdk.carts.commands.CartInStoreCreateCommand;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.*;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.ConcurrentModificationException;
import io.sphere.sdk.client.NotFoundException;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.DiscountCodeDraftDsl;
import io.sphere.sdk.discountcodes.DiscountCodeFixtures;
import io.sphere.sdk.discountcodes.commands.DiscountCodeCreateCommand;
import io.sphere.sdk.discountcodes.commands.DiscountCodeDeleteCommand;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.AddressBuilder;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.commands.OrderDeleteCommand;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.utils.MoneyImpl;

import javax.money.MonetaryAmount;
import java.util.List;
import java.util.function.*;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomerAndCart;
import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class CartFixtures {

    public static final CountryCode DEFAULT_COUNTRY = DE;
    public static final Address GERMAN_ADDRESS = AddressBuilder.of(DEFAULT_COUNTRY).build();

    public static Cart createCart(final BlockingSphereClient client, final CartDraft cartDraft) {
        return client.executeBlocking(CartCreateCommand.of(cartDraft));
    }

    public static Cart createCartWithCountry(final BlockingSphereClient client) {
        return createCart(client, CartDraft.of(EUR).withCountry(DEFAULT_COUNTRY));
    }

    public static Cart createCartWithoutCountry(final BlockingSphereClient client) {
        return createCart(client, CartDraft.of(EUR));
    }

    public static Cart createCartWithShippingAddress(final BlockingSphereClient client) {
        final Cart cart = createCartWithCountry(client);
        return client.executeBlocking(CartUpdateCommand.of(cart, SetShippingAddress.of(GERMAN_ADDRESS)));
    }

    public static void withEmptyCartAndProduct(final BlockingSphereClient client, final BiConsumer<Cart, Product> f) {
        withTaxedProduct(client, product -> {
            final Cart cart = createCartWithCountry(client);
            f.accept(cart, product);
        });
    }

    public static void withCartDraft(final BlockingSphereClient client, final CartDraft draft, final Function<Cart, CartLike<?>> operator) {
        final Cart cart = client.executeBlocking(CartCreateCommand.of(draft));
        delete(client, operator.apply(cart));
    }


    public static void withCart(final BlockingSphereClient client, final UnaryOperator<Cart> operator) {
        final Cart cart = createCartWithCountry(client);
        final Cart cartToDelete = operator.apply(cart);
        try {
            client.executeBlocking(CartDeleteCommand.of(cartToDelete));
        } catch (NotFoundException ignored) {}
    }

    public static void withCart(final BlockingSphereClient client, final Cart cart, final Function<Cart, CartLike<?>> operator) {
        final CartLike<?> cartLike = operator.apply(cart);
        delete(client, cartLike);
    }

    public static void withCartWithKey(final BlockingSphereClient client, final UnaryOperator<Cart> operator) {
        final Cart cart = createCartWithCountry(client);
        final String key = randomKey();
        final Cart updatedCart = client.executeBlocking(CartUpdateCommand.of(cart, SetKey.of(key)));
        assertThat(updatedCart.getKey()).isEqualTo(key);
        final Cart cartToDelete = operator.apply(cart);
        delete(client, cartToDelete);
    }

    private static void delete(final BlockingSphereClient client, final CartLike<?> cartLike) {
        if (cartLike instanceof Cart) {
            final Cart cart = (Cart) cartLike;
            try {
                client.executeBlocking(CartDeleteCommand.of(cart));
            } catch (NotFoundException ignored) {
            } catch (ConcurrentModificationException e) {
                client.executeBlocking(CartDeleteCommand.of(Versioned.of(cart.getId(), e.getCurrentVersion())));
            }
        } else {
            final Order order = (Order) cartLike;
            try {
                client.executeBlocking(OrderDeleteCommand.of(order));
            } catch (NotFoundException ignored) {
            } catch (ConcurrentModificationException e) {
                client.executeBlocking(CartDeleteCommand.of(Versioned.of(order.getId(), e.getCurrentVersion())));
            }
        }
    }

    public static void withFilledCart(final BlockingSphereClient client, final Consumer<Cart> f) {
        withTaxedProduct(client, product -> {
            final Cart cart = createCartWithShippingAddress(client);
            assertThat(cart.getLineItems()).hasSize(0);
            final long quantity = 3;
            final String productId = product.getId();
            final AddLineItem action = AddLineItem.of(productId, 1, quantity);

            final Cart updatedCart = client.executeBlocking(CartUpdateCommand.of(cart, action));
            assertThat(updatedCart.getLineItems()).hasSize(1);
            final LineItem lineItem = updatedCart.getLineItems().get(0);
            assertThat(lineItem.getName()).isEqualTo(product.getMasterData().getStaged().getName());
            assertThat(lineItem.getQuantity()).isEqualTo(quantity);
            f.accept(updatedCart);
            delete(client, updatedCart);
        });
    }

    public static void withFilledCartInStore(final BlockingSphereClient client, final Store store, final Consumer<Cart> f) {
        withTaxedProduct(client, product -> {

            final Cart cart = client.executeBlocking(CartInStoreCreateCommand.of(store.getKey(), CartDraft.of(EUR)
                    .withCountry(DEFAULT_COUNTRY)
                    .withShippingAddress(GERMAN_ADDRESS)
                    .withStore(store.toResourceIdentifier())));
            assertThat(cart.getLineItems()).hasSize(0);
            final long quantity = 3;
            final String productId = product.getId();
            final AddLineItem action = AddLineItem.of(productId, 1, quantity);

            final Cart updatedCart = client.executeBlocking(CartUpdateCommand.of(cart, action));
            assertThat(updatedCart.getLineItems()).hasSize(1);
            final LineItem lineItem = updatedCart.getLineItems().get(0);
            assertThat(lineItem.getName()).isEqualTo(product.getMasterData().getStaged().getName());
            assertThat(lineItem.getQuantity()).isEqualTo(quantity);
            f.accept(updatedCart);
            delete(client, updatedCart);
        });
    }

    public static void withFilledCartWithTaxMode(final BlockingSphereClient client, final TaxMode taxMode, final Consumer<Cart> f) {
        withTaxedProduct(client, product -> {
            final Cart cart = client.executeBlocking(CartUpdateCommand.of(createCartWithShippingAddress(client), ChangeTaxMode.of(taxMode)));
            assertThat(cart.getLineItems()).hasSize(0);
            final long quantity = 3;
            final String productId = product.getId();
            final AddLineItem action = AddLineItem.of(productId, 1, quantity);

            final Cart updatedCart = client.executeBlocking(CartUpdateCommand.of(cart, action));
            assertThat(updatedCart.getLineItems()).hasSize(1);
            final LineItem lineItem = updatedCart.getLineItems().get(0);
            assertThat(lineItem.getName()).isEqualTo(product.getMasterData().getStaged().getName());
            assertThat(lineItem.getQuantity()).isEqualTo(quantity);
            f.accept(updatedCart);
            delete(client, updatedCart);
        });
    }

    public static void withCustomerAndFilledCart(final BlockingSphereClient client, final BiConsumer<Customer, Cart> consumer) {
        withCustomer(client, customer -> {
            withFilledCart(client, cart -> {
                final Cart cartForCustomer = client.executeBlocking(CartUpdateCommand.of(cart, SetCustomerId.of(customer.getId())));
                consumer.accept(customer, cartForCustomer);
            });
        });
    }


    public static void withCartAndTaxedProduct(final BlockingSphereClient client, final BiFunction<Cart, Product, Cart> f) {
        withTaxedProduct(client, product -> {
            final Cart cart = createCartWithShippingAddress(client);

            final Cart cartToDelete = f.apply(cart, product);

            delete(client, cartToDelete);
        });
    }

    public static void withLineItemAndCustomLineItemFilledCart(final BlockingSphereClient client, final UnaryOperator<Cart> op) {
        withTaxedProduct(client, product -> {
            final Cart cart = createCartWithShippingAddress(client);
            assertThat(cart.getLineItems()).hasSize(0);

            final long quantity = 3;
            final String productId = product.getId();
            final AddLineItem addLineItemAction = AddLineItem.of(productId, 1, quantity);

            assertThat(cart.getCustomLineItems()).hasSize(0);
            final MonetaryAmount money = MoneyImpl.of("23.50", EUR);
            final String slug = "thing-slug";
            final LocalizedString name = en("thing");
            final CustomLineItemDraft item = CustomLineItemDraft.of(name, slug, money, product.getTaxCategory(), 5L, null);
            final AddCustomLineItem addCustomLineItemAction = AddCustomLineItem.of(item);

            final Cart updatedCart = client.executeBlocking(CartUpdateCommand.of(cart, asList(addLineItemAction, addCustomLineItemAction)));
            assertThat(updatedCart.getLineItems()).hasSize(1);
            final LineItem lineItem = updatedCart.getLineItems().get(0);
            assertThat(lineItem.getName()).isEqualTo(product.getMasterData().getStaged().getName());
            assertThat(lineItem.getQuantity()).isEqualTo(quantity);
            final Cart cartToDelete = op.apply(updatedCart);
            delete(client, cartToDelete);
        });
    }

    public static void withCustomLineItemFilledCartWithTaxMode(final BlockingSphereClient client, final TaxMode taxMode,  final UnaryOperator<Cart> op) {
        withTaxedProduct(client, product -> {
            final Cart cart = client.executeBlocking(CartUpdateCommand.of(createCartWithShippingAddress(client), ChangeTaxMode.of(taxMode)));

            final MonetaryAmount money = MoneyImpl.of("23.50", EUR);
            final String slug = "thing-slug";
            final LocalizedString name = en("thing");
            final CustomLineItemDraft item = CustomLineItemDraft.of(name, slug, money, product.getTaxCategory(), 5L, null);
            final AddCustomLineItem addCustomLineItemAction = AddCustomLineItem.of(item);

            final Cart updatedCart = client.executeBlocking(CartUpdateCommand.of(cart, asList(addCustomLineItemAction)));

            final Cart cartToDelete = op.apply(updatedCart);
            delete(client, cartToDelete);
        });
    }

    public static void withCartHavingCartDiscountedLineItem(final BlockingSphereClient client, final RelativeCartDiscountValue relativeCartDiscountValue, final UnaryOperator<Cart> op) {
        withTaxedProduct(client, product -> {
            withCustomer(client, (customer) -> {
                CartDiscountFixtures.withCartDiscount(client, builder -> builder
                        .cartPredicate(CartPredicate.of("customer.id=\"" + customer.getId() + "\""))
                        .value(relativeCartDiscountValue)
                        .target(LineItemsTarget.of("product.id=\"" + product.getId() + "\"")), cartDiscount -> {
                    withCart(client, (cart) -> {
                        assertThat(cart.getLineItems()).hasSize(0);
                        final long quantity = 3;
                        final String productId = product.getId();
                        final AddLineItem addLineItemAction = AddLineItem.of(productId, 1, quantity);
                        final List<UpdateAction<Cart>> updateActions =
                                asList(addLineItemAction, SetCustomerId.of(customer.getId()), Recalculate.of().withUpdateProductData(true));
                        final Cart updatedCart = client.executeBlocking(CartUpdateCommand.of(cart, updateActions));
                        final Cart cartToDelete = op.apply(updatedCart);
                        return cartToDelete;
                    });
                });
            });
        });
    }

    public static void withCartHavingDiscountedCustomLineItem(final BlockingSphereClient client, final RelativeCartDiscountValue relativeCartDiscountValue, final UnaryOperator<Cart> op) {
        withTaxedProduct(client, product -> {
            withCustomer(client, (customer) -> {
                CartDiscountFixtures.withCartDiscount(client, builder -> builder
                        .cartPredicate(CartPredicate.of("customer.id=\"" + customer.getId() + "\""))
                        .value(relativeCartDiscountValue)
                        .target(CustomLineItemsTarget.of("slug =\"thing-discounted-slug\"")), cartDiscount -> {
                    withCart(client, (cart) -> {
                        assertThat(cart.getCustomLineItems()).hasSize(0);
                        final MonetaryAmount money = MoneyImpl.of("23.50", EUR);
                        final String slug = "thing-discounted-slug";
                        final LocalizedString name = en("thing");
                        final CustomLineItemDraft item = CustomLineItemDraft.of(name, slug, money, product.getTaxCategory(), 5L, null);
                        final AddCustomLineItem addCustomLineItemAction = AddCustomLineItem.of(item);
                        final List<UpdateAction<Cart>> updateActions =
                                asList(addCustomLineItemAction, SetCustomerId.of(customer.getId()), Recalculate.of().withUpdateProductData(true));
                        final Cart updatedCart = client.executeBlocking(CartUpdateCommand.of(cart, updateActions));
                        final Cart cartToDelete = op.apply(updatedCart);
                        return cartToDelete;
                    });
                });
            });
        });
    }

    public static void withCartAndDiscountCode(final BlockingSphereClient client, final BiFunction<Cart, DiscountCode, Cart> user) {
        withCustomerAndCart(client, (customer, cart) -> {
            final CartDiscountDraft draft = CartDiscountFixtures.newCartDiscountDraftBuilder()
                    .cartPredicate(CartPredicate.of(format("customer.id = \"%s\"", customer.getId())))
                    .requiresDiscountCode(true)
                    .isActive(true)
                    .validFrom(null)
                    .validUntil(null)
                    .build();
            final CartDiscount cartDiscount = client.executeBlocking(CartDiscountCreateCommand.of(draft));
            final DiscountCode discountCode = client.executeBlocking(DiscountCodeCreateCommand.of(DiscountCodeDraftDsl.of(randomKey(), cartDiscount)));
            final Cart updatedCart = user.apply(cart, discountCode);
            client.executeBlocking(CartDeleteCommand.of(updatedCart));
            delete(client, updatedCart);
            try {
                client.executeBlocking(DiscountCodeDeleteCommand.of(discountCode));
            } catch (NotFoundException ignored) {}
            try {
                client.executeBlocking(CartDiscountDeleteCommand.of(cartDiscount));
            } catch (NotFoundException ignored) {}
        });
    }

    public static void withCartWithLineItems(final BlockingSphereClient client, final List<LineItemDraft> lineItemsDraft, final UnaryOperator<Cart> op) {
        final CartDraftDsl cartDraft = CartDraft.of(EUR)
                .withTaxMode(TaxMode.EXTERNAL)
                .withCountry(DE)
                .withShippingAddress(Address.of(DE))
                .withLineItems(lineItemsDraft);
        final CartCreateCommand cmd = CartCreateCommand.of(cartDraft);
        final Cart cart = client.executeBlocking(cmd);
        final Cart updatedCart = op.apply(cart);
        delete(client, updatedCart);
    }
}
