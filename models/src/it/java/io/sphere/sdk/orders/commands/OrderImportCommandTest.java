package io.sphere.sdk.orders.commands;

import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.orders.*;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import javax.money.MonetaryAmount;

import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.fest.assertions.Assertions.assertThat;

public class OrderImportCommandTest extends IntegrationTest {
    @Test
    public void minimalOrder() throws Exception {
        withProduct(client(), product -> {
            final String productId = product.getId();
            final int variantId = 1;
            final LocalizedStrings name = en("a name");
            final int quantity = 1;
            final Price price = Price.of(EURO_10);
            final OrderState orderState = OrderState.OPEN;
            final MonetaryAmount amount = EURO_10;
            final ImportProductVariant variant = ImportProductVariantBuilder.of(productId, variantId).build();

            final ImportLineItem importLineItem = ImportLineItemBuilder.of(variant, quantity, price, name).build();
            final ImportOrder importOrder = ImportOrderBuilder.ofLineItems(amount, orderState, asList(importLineItem)).build();
            final OrderImportCommand cmd = OrderImportCommand.of(importOrder);

            final Order order = execute(cmd);

            assertThat(order.getOrderState()).isEqualTo(orderState);
            assertThat(order.getTotalPrice()).isEqualTo(amount);
            assertThat(order.getLineItems()).hasSize(1);
            final LineItem lineItem = order.getLineItems().get(0);
            assertThat(lineItem.getName()).isEqualTo(name);
            assertThat(lineItem.getProductId()).isEqualTo(productId);
            assertThat(lineItem.getVariant().getId()).isEqualTo(variantId);
            assertThat(lineItem.getName()).isEqualTo(name);
            assertThat(lineItem.getQuantity()).isEqualTo(quantity);
            assertThat(lineItem.getPrice()).isEqualTo(price);
        });
    }
}