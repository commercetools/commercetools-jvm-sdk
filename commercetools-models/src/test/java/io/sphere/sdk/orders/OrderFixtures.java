package io.sphere.sdk.orders;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.*;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.AddCustomLineItem;
import io.sphere.sdk.carts.commands.updateactions.SetCustomShippingMethod;
import io.sphere.sdk.carts.commands.updateactions.SetCustomerEmail;
import io.sphere.sdk.carts.queries.CartByIdGet;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.ConcurrentModificationException;
import io.sphere.sdk.client.NotFoundException;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerFixtures;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.customers.commands.CustomerSignInCommand;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.orderedits.commands.OrderEditDeleteCommand;
import io.sphere.sdk.orders.commands.OrderDeleteCommand;
import io.sphere.sdk.orders.commands.OrderFromCartCreateCommand;
import io.sphere.sdk.orders.commands.OrderUpdateCommand;
import io.sphere.sdk.orders.commands.updateactions.AddDelivery;
import io.sphere.sdk.orders.commands.updateactions.AddReturnInfo;
import io.sphere.sdk.orders.commands.updateactions.ChangePaymentState;
import io.sphere.sdk.orders.commands.updateactions.ChangeShipmentState;
import io.sphere.sdk.products.BySkuVariantIdentifier;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxCategoryFixtures;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.TypeFixtures;
import io.sphere.sdk.utils.MoneyImpl;
import org.assertj.core.api.Assertions;

import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static io.sphere.sdk.carts.CartFixtures.createCartWithShippingAddress;
import static io.sphere.sdk.carts.CartFixtures.withFilledCart;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethodForGermany;
import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTaxCategory;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;

public class OrderFixtures {

    public static final String CUSTOMER_EMAIL = "foo@bar.tld";

    public static void withOrder(final BlockingSphereClient client, final UnaryOperator<Order> op) {
        withCustomer(client, customer -> withOrder(client, customer, op));
    }

    public static void withOrder(final BlockingSphereClient client, final Consumer<Order> consumer) {
        withCustomer(client, customer -> withOrder(client, customer, consumer));
    }

    public static void withNonUpdatedOrder(final BlockingSphereClient client, final UnaryOperator<Order> op) {
        withCustomer(client, customer -> withNonUpdatedOrder(client, customer, op)
        );
    }

    public static void withOrder(final BlockingSphereClient client, final Customer customer, final UnaryOperator<Order> op) {
        withFilledCart(client, cart -> {
            final Order updatedOrder = createOrderFromCart(client, customer, cart);
            final Order orderToDelete = op.apply(updatedOrder);
            delete(client, orderToDelete);
        });
    }

    public static void withOrder(final BlockingSphereClient client, final Customer customer, final Consumer<Order> consumer) {
        withFilledCart(client, cart -> {
            final Order updatedOrder = createOrderFromCart(client, customer, cart);
            consumer.accept(updatedOrder);
            delete(client, updatedOrder);
        });
    }

    public static void withOrder(final BlockingSphereClient client, final Customer customer, final Cart cart, final UnaryOperator<Order> op) {
            final Order updatedOrder = createOrderFromCart(client, customer, cart);
            final Order orderToDelete = op.apply(updatedOrder);
            delete(client, orderToDelete);
    }

    public static void withOrderInStore(final BlockingSphereClient client, final Store store, final Function<Order, Order> f) {

        withCustomer(client, customer -> {
            final String customerId = customer.getId();
            final String customerEmail = customer.getEmail();
            withUpdateableType(client, type -> {
                final CustomFieldsDraft customFieldsDraft =
                        CustomFieldsDraft.ofTypeKeyAndObjects(type.getKey(), singletonMap(TypeFixtures.STRING_FIELD_NAME, "foo"));
                withShippingMethodForGermany(client, shippingMethod -> {
                    withTaxedProduct(client, product1 -> {
                        withTaxedProduct(client, product2 -> {
                            withTaxedProduct(client, product3 -> {
                                final LineItemDraft lineItemDraft1 = LineItemDraft.of(product1, 1, 15);
                                final LineItemDraft lineItemDraftOfVariantIdentifier = LineItemDraftBuilder.ofVariantIdentifier(product2.getMasterData().getStaged().getMasterVariant().getIdentifier(), 25L).build();
                                String sku = product3.getMasterData().getStaged().getMasterVariant().getSku();
                                final LineItemDraft lineItemDraftOfSku = LineItemDraftBuilder.ofSkuVariantIdentifier(BySkuVariantIdentifier.of(sku), 35L).build();
                                final List<LineItemDraft> lineItems = asList(lineItemDraft1, lineItemDraftOfVariantIdentifier, lineItemDraftOfSku);

                                final List<CustomLineItemDraft> customLineItems = singletonList(CustomLineItemDraft.of(randomSlug(), "foo-bar", EURO_5, product1.getTaxCategory(), 1L, null));

                                final Address shippingAddress = Address.of(CountryCode.DE).withAdditionalAddressInfo("shipping");
                                final Address billingAddress = Address.of(CountryCode.DE).withAdditionalAddressInfo("billing");

                                final CartDraft cartDraft = CartDraft.of(EUR)
                                        .withCountry(DE)
                                        .withLocale(Locale.GERMAN)
                                        .withCustomerId(customerId)
                                        .withCustomerEmail(customerEmail)
                                        .withLineItems(lineItems)
                                        .withCustomLineItems(customLineItems)
                                        .withBillingAddress(billingAddress)
                                        .withShippingAddress(shippingAddress)
                                        .withShippingMethod(shippingMethod)
                                        .withCustom(customFieldsDraft);
                                CartFixtures.withCartDraft(client, cartDraft, cart -> {
                                    Order order = client.executeBlocking(OrderFromCartCreateCommand.of(cart));
                                    order = f.apply(order);
                                    client.executeBlocking(OrderDeleteCommand.of(order));
                                    cart = client.executeBlocking(CartByIdGet.of(cart.getId()));
                                    return cart;
                                });
                            });
                        });
                    });
                });
                return type;
            });
        });
    }

    private static Order createOrderFromCart(BlockingSphereClient client, Customer customer, Cart cart) {
        final TaxCategory taxCategory = TaxCategoryFixtures.defaultTaxCategory(client);
        final SetCustomShippingMethod shippingMethodAction = SetCustomShippingMethod.of("custom shipping method", ShippingRate.of(EURO_10), taxCategory);
        final SetCustomerEmail emailAction = SetCustomerEmail.of(CUSTOMER_EMAIL);
        client.executeBlocking(CartUpdateCommand.of(cart, asList(shippingMethodAction, emailAction)));

        final CustomerSignInCommand signInCommand = CustomerSignInCommand.of(customer.getEmail(), CustomerFixtures.PASSWORD, cart.getId());
        final CustomerSignInResult signInResult = client.executeBlocking(signInCommand);

        final Order order = client.executeBlocking(OrderFromCartCreateCommand.of(signInResult.getCart()));
        return client.executeBlocking(OrderUpdateCommand.of(order, asList(
                ChangeShipmentState.of(ShipmentState.READY),
                ChangePaymentState.of(PaymentState.PENDING)
        )));
    }

    public static void withNonUpdatedOrder(final BlockingSphereClient client, final Customer customer, final UnaryOperator<Order> op) {
        withFilledCart(client, cart -> {
            final TaxCategory taxCategory = TaxCategoryFixtures.defaultTaxCategory(client);
            final SetCustomShippingMethod shippingMethodAction = SetCustomShippingMethod.of("custom shipping method", ShippingRate.of(EURO_10), taxCategory);
            final SetCustomerEmail emailAction = SetCustomerEmail.of(CUSTOMER_EMAIL);
            final Cart updatedCart = client.executeBlocking(CartUpdateCommand.of(cart, asList(shippingMethodAction, emailAction)));

            final CustomerSignInCommand signInCommand = CustomerSignInCommand.of(customer.getEmail(), CustomerFixtures.PASSWORD, cart.getId());
            final CustomerSignInResult signInResult = client.executeBlocking(signInCommand);

            final Order order = client.executeBlocking(OrderFromCartCreateCommand.of(signInResult.getCart()));
            final Order orderToDelete = op.apply(order);
            delete(client, orderToDelete);
        });
    }

    public static void withOrderOfCustomLineItems(final BlockingSphereClient client, final Consumer<Order> f) {
        withTaxCategory(client, taxCategory -> {
            final Cart cart = createCartWithShippingAddress(client);
            final MonetaryAmount money = MoneyImpl.of("23.50", EUR);
            final String slug = "thing-slug";
            final LocalizedString name = en("thing");
            final long quantity = 5;
            final CustomLineItemDraft item = CustomLineItemDraft.of(name, slug, money, taxCategory, quantity, null);
            final Cart cartWith5 = client.executeBlocking(CartUpdateCommand.of(cart, AddCustomLineItem.of(item)));
            final Order order = client.executeBlocking(OrderFromCartCreateCommand.of(cartWith5));
            f.accept(order);
            delete(client, order);
        });
    }

    public static void withOrderAndReturnInfo(final BlockingSphereClient client, final BiFunction<Order, ReturnInfo, Order> f) {
        withOrder(client, order -> {
            Assertions.assertThat(order.getReturnInfo()).isEmpty();
            final String lineItemId = order.getLineItems().get(0).getId();
            final List<LineItemReturnItemDraft> items = asList(LineItemReturnItemDraft.of(1L, lineItemId, ReturnShipmentState.RETURNED, "foo bar"));
            final AddReturnInfo action = AddReturnInfo.of(items);
            final AddDelivery addDelivery = AddDelivery.of(asList(DeliveryItem.of(lineItemId, 1)));
            final Order updatedOrder = client.executeBlocking(OrderUpdateCommand.of(order, asList(action, addDelivery)));

            final ReturnInfo returnInfo = updatedOrder.getReturnInfo().get(0);
            return f.apply(updatedOrder, returnInfo);
        });
    }

    private static void delete(final BlockingSphereClient client, final Order order) {
        try {
            client.executeBlocking(OrderDeleteCommand.of(order));
        } catch (NotFoundException ignored) {
        } catch (ConcurrentModificationException e) {
            client.executeBlocking(OrderDeleteCommand.of(Versioned.of(order.getId(), e.getCurrentVersion())));
        }
    }
}
