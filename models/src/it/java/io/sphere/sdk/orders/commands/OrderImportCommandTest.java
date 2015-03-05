package io.sphere.sdk.orders.commands;

import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.orders.*;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.fest.assertions.Assertions.assertThat;

public class OrderImportCommandTest extends IntegrationTest {
    @Test
    public void minimalOrder() throws Exception {
        withProduct(client(), product -> {
            final ImportProductVariant variant = ImportProductVariantBuilder.of(product.getId(), 1).build();
            final LocalizedStrings name = en("a name");
            final ImportLineItem importLineItem = ImportLineItemBuilder.of(variant, 1, Price.of(EURO_10), name).build();
            final ImportOrder importOrder = ImportOrderBuilder.ofLineItems(EURO_10, OrderState.OPEN, asList(importLineItem)).build();
            final OrderImportCommand cmd = OrderImportCommand.of(importOrder);
            final Order order = execute(cmd);
            assertThat(order.getLineItems()).hasSize(1);
            assertThat(order.getLineItems().get(0).getName()).isEqualTo(name);
        });
    }
}