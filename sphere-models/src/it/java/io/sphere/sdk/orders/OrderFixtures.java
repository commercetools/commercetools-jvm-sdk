package io.sphere.sdk.orders;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.carts.CustomLineItemDraft;
import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.AddCustomLineItem;
import io.sphere.sdk.carts.commands.updateactions.SetCustomShippingMethod;
import io.sphere.sdk.carts.commands.updateactions.SetCustomerEmail;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.customers.CustomerFixtures;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.customers.commands.CustomerSignInCommand;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.orders.commands.OrderFromCartCreateCommand;
import io.sphere.sdk.orders.commands.OrderUpdateCommand;
import io.sphere.sdk.orders.commands.updateactions.AddReturnInfo;
import io.sphere.sdk.orders.commands.updateactions.ChangePaymentState;
import io.sphere.sdk.orders.commands.updateactions.ChangeShipmentState;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxCategoryFixtures;
import io.sphere.sdk.utils.MoneyImpl;
import org.assertj.core.api.Assertions;

import javax.money.MonetaryAmount;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static io.sphere.sdk.carts.CartFixtures.*;
import static io.sphere.sdk.customers.CustomerFixtures.*;
import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderFixtures {

    public static final String CUSTOMER_EMAIL = "foo@bar.tld";

    public static void withOrder(final TestClient client, final Consumer<Order> f) {
        withCustomer(client, customer ->
            withFilledCart(client, cart -> {
                final TaxCategory taxCategory = TaxCategoryFixtures.defaultTaxCategory(client);
                final SetCustomShippingMethod shippingMethodAction = SetCustomShippingMethod.of("custom shipping method", ShippingRate.of(EURO_10), taxCategory);
                final SetCustomerEmail emailAction = SetCustomerEmail.of(CUSTOMER_EMAIL);
                final Cart updatedCart = client.execute(CartUpdateCommand.of(cart, asList(shippingMethodAction, emailAction)));

                final CustomerSignInCommand signInCommand = CustomerSignInCommand.of(customer.getEmail(), CustomerFixtures.PASSWORD, Optional.of(cart.getId()));
                final CustomerSignInResult signInResult = client.execute(signInCommand);

                final Order order = client.execute(OrderFromCartCreateCommand.of(signInResult.getCart().get()));

                final Order updatedOrder = client.execute(OrderUpdateCommand.of(order, asList(
                        ChangeShipmentState.of(ShipmentState.READY),
                        ChangePaymentState.of(PaymentState.PENDING)
                )));
                f.accept(updatedOrder);
            })
        );
    }

    public static void withOrderOfCustomLineItems(final TestClient client, final Consumer<Order> f) {
        withTaxCategory(client, taxCategory -> {
            final Cart cart = createCartWithShippingAddress(client);
            final MonetaryAmount money = MoneyImpl.of("23.50", EUR);
            final String slug = "thing-slug";
            final LocalizedStrings name = en("thing");
            final long quantity = 5;
            final CustomLineItemDraft item = CustomLineItemDraft.of(name, slug, money, taxCategory, quantity);
            final Cart cartWith5 = client.execute(CartUpdateCommand.of(cart, AddCustomLineItem.of(item)));
            final Order order = client.execute(OrderFromCartCreateCommand.of(cartWith5));
            f.accept(order);
        });
    }

    public static void withOrderAndReturnInfo(final TestClient client, final BiConsumer<Order, ReturnInfo> f) {
        withOrder(client, order -> {
            Assertions.assertThat(order.getReturnInfo()).isEmpty();
            final String lineItemId = order.getLineItems().get(0).getId();
            final List<ReturnItemDraft> items = asList(ReturnItemDraft.of(1, lineItemId, ReturnShipmentState.RETURNED, "foo bar"));
            final AddReturnInfo action = AddReturnInfo.of(items);
            final Order updatedOrder = client.execute(OrderUpdateCommand.of(order, action));

            final ReturnInfo returnInfo = updatedOrder.getReturnInfo().get(0);
            f.accept(updatedOrder, returnInfo);
        });
    }
}
