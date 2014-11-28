package io.sphere.sdk.orders;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.SetCustomShippingMethod;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.orders.commands.OrderFromCartCreateCommand;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxCategoryFixtures;

import java.util.function.Consumer;

import static io.sphere.sdk.carts.CartFixtures.withFilledCart;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class OrderFixtures {
    public static void withOrder(final TestClient client, final Consumer<Order> f) {
        withFilledCart(client, cart -> {
            final TaxCategory taxCategory = TaxCategoryFixtures.defaultTaxCategory(client);
            final SetCustomShippingMethod shippingMethodAction = SetCustomShippingMethod.of("custom shipping method", ShippingRate.of(EURO_10), taxCategory);
            final Cart updatedCart = client.execute(new CartUpdateCommand(cart, shippingMethodAction));
            final Order order = client.execute(OrderFromCartCreateCommand.of(updatedCart));
            f.accept(order);
        });
    }
}
