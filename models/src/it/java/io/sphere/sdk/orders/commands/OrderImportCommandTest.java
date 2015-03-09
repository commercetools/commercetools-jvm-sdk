package io.sphere.sdk.orders.commands;

import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.orders.*;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import javax.money.MonetaryAmount;

import java.time.Instant;
import java.util.function.Consumer;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;

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

    @Test
    public void customerId() throws Exception {
        withCustomer(client(), customer -> {
            final String customerId = customer.getId();
            testOrderAspect(builder -> {
                        builder.customerId(customerId);
                    },
                    order -> assertThat(order.getCustomerId()).isPresentAs(customerId));
        });
    }

    @Test
    public void orderNumber() throws Exception {
        final String orderNumber = randomString();
        testOrderAspect(builder -> builder.orderNumber(orderNumber),
                order -> assertThat(order.getOrderNumber()).isPresentAs(orderNumber));
    }

    @Test
    public void billingAddress() throws Exception {
        final Address billingAddress = randomAddress();
        testOrderAspect(builder -> builder.billingAddress(billingAddress),
                order -> assertThat(order.getBillingAddress()).isPresentAs(billingAddress));
    }

    @Test
    public void getCompletedAt() throws Exception {
        final Instant completedAt = Instant.now().minusSeconds(5555);
        testOrderAspect(builder -> builder.completedAt(completedAt),
                order -> assertThat(order.getCompletedAt()).isPresentAs(completedAt));
    }

    private void testOrderAspect(final Consumer<ImportOrderBuilder> orderBuilderConsumer, final Consumer<Order> orderConsumer) {
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
            final ImportOrderBuilder importOrderBuilder = ImportOrderBuilder.ofLineItems(amount, orderState, asList(importLineItem));
            orderBuilderConsumer.accept(importOrderBuilder);
            final ImportOrder importOrder = importOrderBuilder.build();
            final OrderImportCommand cmd = OrderImportCommand.of(importOrder);

            final Order order = execute(cmd);
            orderConsumer.accept(order);
        });
    }
}