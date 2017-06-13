package io.sphere.sdk.carts;

import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.AddLineItem;
import io.sphere.sdk.carts.commands.updateactions.ChangeLineItemQuantity;
import io.sphere.sdk.carts.commands.updateactions.RemoveLineItem;
import io.sphere.sdk.test.IntegrationTest;
import org.javamoney.moneta.Money;
import org.junit.Test;

import javax.money.MonetaryAmount;

import static io.sphere.sdk.carts.CartFixtures.withCartWithLineItems;
import static io.sphere.sdk.carts.CartFixtures.withEmptyCartAndProduct;
import static io.sphere.sdk.carts.LineItemPriceMode.EXTERNAL_PRICE;
import static io.sphere.sdk.carts.LineItemPriceMode.EXTERNAL_TOTAL;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class ExternalPricesIntegrationTest extends IntegrationTest {

    @Test
    public void createCartWithALineItemWithAnExternalPrice() {
        withProduct(client(), product -> {
            final int quantity = 5;
            final int variantId = 1;
            final Money externalPrice = Money.from(EURO_12);
            final LineItemDraft lineItemDraft = LineItemDraft.of(product, variantId, quantity)
                    .withExternalPrice(externalPrice);
            withCartWithLineItems(client(), singletonList(lineItemDraft), cart -> {
                final LineItem cartLineItem = cart.getLineItems().get(0);
                assertThat(cartLineItem.getPrice().getValue()).isEqualTo(externalPrice);
                assertThat(cartLineItem.getPriceMode()).isEqualTo(EXTERNAL_PRICE);
                return cart;
            });
        });
    }

    @Test
    public void createCartWithALineItemWithExternalTotalPrice() {
        withProduct(client(), product -> {
            final int quantity = 5;
            final int variantId = 1;
            final MonetaryAmount price = EURO_20;
            final MonetaryAmount totalPrice = EURO_25;
            final ExternalLineItemTotalPrice externalLineItemTotalPrice = ExternalLineItemTotalPrice.ofPriceAndTotalPrice(price, totalPrice);
            final LineItemDraft lineItemDraft = LineItemDraft.of(product, variantId, quantity)
                    .withExternalTotalPrice(externalLineItemTotalPrice);
            withCartWithLineItems(client(), singletonList(lineItemDraft), cart -> {
                final LineItem cartLineItem = cart.getLineItems().get(0);
                assertThat(cartLineItem.getPrice().getValue()).isEqualTo(price);
                assertThat(cartLineItem.getTotalPrice()).isEqualTo(totalPrice);
                assertThat(cartLineItem.getPriceMode()).isEqualTo(EXTERNAL_TOTAL);
                return cart;
            });
        });
    }

    @Test
    public void addLineItemWithExternalPrice() {
        withEmptyCartAndProduct(client(), (cart, product) -> {
            assertThat(cart.getLineItems()).isEmpty();
            final int quantity = 5;
            final int variantId = 1;
            final String productId = product.getId();
            final Money externalPrice = Money.from(EURO_30);
            final AddLineItem updateAction = AddLineItem.of(productId, variantId, quantity)
                    .withExternalPrice(externalPrice);
            final Cart cartWithLineItem = client().executeBlocking(CartUpdateCommand.of(cart, updateAction));
            final LineItem lineItem = cartWithLineItem.getLineItems().get(0);
            assertThat(lineItem.getPrice().getValue()).isEqualTo(externalPrice);
            assertThat(lineItem.getPriceMode()).isEqualTo(EXTERNAL_PRICE);
        });
    }

    @Test
    public void addLineItemWithExternalTotalPrice() {
        withEmptyCartAndProduct(client(), (cart, product) -> {
            assertThat(cart.getLineItems()).isEmpty();
            final int quantity = 5;
            final int variantId = 1;
            final String productId = product.getId();
            final MonetaryAmount price = EURO_20;
            final MonetaryAmount totalPrice = EURO_25;
            final ExternalLineItemTotalPrice externalLineItemTotalPrice = ExternalLineItemTotalPrice.ofPriceAndTotalPrice(price, totalPrice);
            final AddLineItem updateAction = AddLineItem.of(productId, variantId, quantity)
                    .withExternalTotalPrice(externalLineItemTotalPrice);
            final Cart cartWithLineItem = client().executeBlocking(CartUpdateCommand.of(cart, updateAction));
            final LineItem lineItem = cartWithLineItem.getLineItems().get(0);
            assertThat(lineItem.getPrice().getValue()).isEqualTo(price);
            assertThat(lineItem.getTotalPrice()).isEqualTo(totalPrice);
            assertThat(lineItem.getPriceMode()).isEqualTo(EXTERNAL_TOTAL);
        });
    }

    @Test
    public void removeLineItemWithExternalPrice() throws Exception {
        withProduct(client(), product -> {
            final int quantity = 5;
            final int variantId = 1;
            final Money initialExternalPrice = Money.from(EURO_20);
            final LineItemDraft lineItemDraft = LineItemDraft.of(product, variantId, quantity).withExternalPrice(initialExternalPrice);
            withCartWithLineItems(client(), singletonList(lineItemDraft), cart -> {
                final LineItem cartLineItem = cart.getLineItems().get(0);
                assertThat(cartLineItem.getQuantity()).isEqualTo(quantity);

                final Money newExternalPrice = Money.from(EURO_25);
                final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, RemoveLineItem.ofLineItemAndExternalPrice(cartLineItem, 4L, newExternalPrice)));
                final LineItem updatedLineItem = updatedCart.getLineItems().get(0);
                assertThat(updatedLineItem.getQuantity()).isEqualTo(1);
                assertThat(updatedLineItem.getPrice().getValue()).isEqualTo(newExternalPrice);
                assertThat(cartLineItem.getPriceMode()).isEqualTo(EXTERNAL_PRICE);

                return updatedCart;
            });
        });
    }

    @Test
    public void removeLineItemWithExternalTotalPrice() throws Exception {
        withProduct(client(), product -> {
            final int quantity = 5;
            final int variantId = 1;
            final MonetaryAmount price = EURO_1;
            final MonetaryAmount totalPrice = EURO_5;
            final ExternalLineItemTotalPrice externalLineItemTotalPrice = ExternalLineItemTotalPrice.ofPriceAndTotalPrice(price, totalPrice);
            final LineItemDraft lineItemDraft = LineItemDraft.of(product, variantId, quantity).withExternalTotalPrice(externalLineItemTotalPrice);
            withCartWithLineItems(client(), singletonList(lineItemDraft), cart -> {
                final LineItem cartLineItem = cart.getLineItems().get(0);
                assertThat(cartLineItem.getQuantity()).isEqualTo(quantity);

                final MonetaryAmount newPrice = EURO_20;
                final MonetaryAmount newTotalPrice = EURO_25;
                final ExternalLineItemTotalPrice newExternalLineItemTotalPrice = ExternalLineItemTotalPrice.ofPriceAndTotalPrice(newPrice, newTotalPrice);
                final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, RemoveLineItem.ofLineItemAndExternalTotalPrice(cartLineItem, 4L, newExternalLineItemTotalPrice)));
                final LineItem updatedLineItem = updatedCart.getLineItems().get(0);
                assertThat(updatedLineItem.getQuantity()).isEqualTo(1);
                assertThat(updatedLineItem.getPrice().getValue()).isEqualTo(newExternalLineItemTotalPrice.getPrice());
                assertThat(updatedLineItem.getTotalPrice()).isEqualTo(newExternalLineItemTotalPrice.getTotalPrice());
                assertThat(cartLineItem.getPriceMode()).isEqualTo(EXTERNAL_TOTAL);

                return updatedCart;
            });
        });
    }

    @Test
    public void changeLineItemQuantityWithExternalPrice() throws Exception {
        withProduct(client(), product -> {
            final int quantity = 5;
            final int variantId = 1;
            final Money initialExternalPrice = Money.from(EURO_20);
            final LineItemDraft lineItemDraft = LineItemDraft.of(product, variantId, quantity).withExternalPrice(initialExternalPrice);
            withCartWithLineItems(client(), singletonList(lineItemDraft), cart -> {
                final LineItem cartLineItem = cart.getLineItems().get(0);
                assertThat(cartLineItem.getQuantity()).isEqualTo(quantity);

                final Money newExternalPrice = Money.from(EURO_25);
                final long newQuantity = 3L;
                final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, ChangeLineItemQuantity.ofLineItemAndExternalPrice(cartLineItem, newQuantity, newExternalPrice)));
                final LineItem updatedLineItem = updatedCart.getLineItems().get(0);
                assertThat(updatedLineItem.getQuantity()).isEqualTo(newQuantity);
                assertThat(updatedLineItem.getPrice().getValue()).isEqualTo(newExternalPrice);
                assertThat(cartLineItem.getPriceMode()).isEqualTo(EXTERNAL_PRICE);

                return updatedCart;
            });
        });
    }

    @Test
    public void changeLineItemQuantityWithExternalTotalPrice() throws Exception {
        withProduct(client(), product -> {
            final int quantity = 5;
            final int variantId = 1;
            final MonetaryAmount price = EURO_1;
            final MonetaryAmount totalPrice = EURO_5;
            final ExternalLineItemTotalPrice externalLineItemTotalPrice = ExternalLineItemTotalPrice.ofPriceAndTotalPrice(price, totalPrice);
            final LineItemDraft lineItemDraft = LineItemDraft.of(product, variantId, quantity).withExternalTotalPrice(externalLineItemTotalPrice);
            withCartWithLineItems(client(), singletonList(lineItemDraft), cart -> {
                final LineItem cartLineItem = cart.getLineItems().get(0);
                assertThat(cartLineItem.getQuantity()).isEqualTo(quantity);

                final MonetaryAmount newPrice = EURO_20;
                final MonetaryAmount newTotalPrice = EURO_25;
                final ExternalLineItemTotalPrice newExternalLineItemTotalPrice = ExternalLineItemTotalPrice.ofPriceAndTotalPrice(newPrice, newTotalPrice);
                final long newQuantity = 3L;
                final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, ChangeLineItemQuantity.ofLineItemAndExternalTotalPrice(cartLineItem, newQuantity, newExternalLineItemTotalPrice)));
                final LineItem updatedLineItem = updatedCart.getLineItems().get(0);
                assertThat(updatedLineItem.getQuantity()).isEqualTo(newQuantity);
                assertThat(updatedLineItem.getPrice().getValue()).isEqualTo(newExternalLineItemTotalPrice.getPrice());
                assertThat(updatedLineItem.getTotalPrice()).isEqualTo(newExternalLineItemTotalPrice.getTotalPrice());
                assertThat(cartLineItem.getPriceMode()).isEqualTo(EXTERNAL_TOTAL);

                return updatedCart;
            });
        });
    }

}
