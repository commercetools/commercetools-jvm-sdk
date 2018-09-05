package io.sphere.sdk.orders;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CustomLineItemDraft;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.AddCustomLineItem;
import io.sphere.sdk.carts.commands.updateactions.SetCustomShippingMethod;
import io.sphere.sdk.carts.commands.updateactions.SetCustomerEmail;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerFixtures;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.customers.commands.CustomerSignInCommand;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.orders.commands.OrderDeleteCommand;
import io.sphere.sdk.orders.commands.OrderFromCartCreateCommand;
import io.sphere.sdk.orders.commands.OrderUpdateCommand;
import io.sphere.sdk.orders.commands.updateactions.*;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxCategoryFixtures;
import io.sphere.sdk.utils.MoneyImpl;
import org.assertj.core.api.Assertions;

import javax.money.MonetaryAmount;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import static io.sphere.sdk.carts.CartFixtures.createCartWithShippingAddress;
import static io.sphere.sdk.carts.CartFixtures.withFilledCart;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTaxCategory;
import static io.sphere.sdk.test.SphereTestUtils.*;

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
            client.executeBlocking(OrderDeleteCommand.of(orderToDelete));
        });
    }

    public static void withOrder(final BlockingSphereClient client, final Customer customer, final Consumer<Order> consumer) {
        withFilledCart(client, cart -> {
            final Order updatedOrder = createOrderFromCart(client, customer, cart);
            consumer.accept(updatedOrder);
        });
    }

    public static void withOrder(final BlockingSphereClient client, final Customer customer, final Cart cart, final UnaryOperator<Order> op) {
            final Order updatedOrder = createOrderFromCart(client, customer, cart);
            final Order orderToDelete = op.apply(updatedOrder);
            client.executeBlocking(OrderDeleteCommand.of(orderToDelete));
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
            client.executeBlocking(OrderDeleteCommand.of(orderToDelete));
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
        });
    }

    public static void withOrderAndReturnInfo(final BlockingSphereClient client, final BiFunction<Order, ReturnInfo, Order> f) {
        withOrder(client, order -> {
            Assertions.assertThat(order.getReturnInfo()).isEmpty();
            final String lineItemId = order.getLineItems().get(0).getId();
            final List<ReturnItemDraft> items = asList(ReturnItemDraft.of(1L, lineItemId, ReturnShipmentState.RETURNED, "foo bar"));
            final AddReturnInfo action = AddReturnInfo.of(items);
            final AddDelivery addDelivery = AddDelivery.of(asList(DeliveryItem.of(lineItemId, 1)));
            final Order updatedOrder = client.executeBlocking(OrderUpdateCommand.of(order, asList(action, addDelivery)));

            final ReturnInfo returnInfo = updatedOrder.getReturnInfo().get(0);
            return f.apply(updatedOrder, returnInfo);
        });
    }
}
