package io.sphere.sdk.carts;

import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.AddLineItem;
import io.sphere.sdk.carts.commands.updateactions.ChangeLineItemQuantity;
import io.sphere.sdk.carts.commands.updateactions.RemoveLineItem;
import io.sphere.sdk.carts.commands.updateactions.SetLineItemPrice;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import javax.money.MonetaryAmount;

import static io.sphere.sdk.carts.CartFixtures.withCartWithLineItems;
import static io.sphere.sdk.carts.CartFixtures.withEmptyCartAndProduct;
import static io.sphere.sdk.carts.LineItemPriceMode.*;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class ExternalPricesIntegrationTest extends IntegrationTest {

    @Test
    public void createCartWithALineItemWithAnExternalPrice() {
        withTaxedProduct(client(), product -> {
            final int quantity = 5;
            final int variantId = 1;
            final MonetaryAmount externalPrice = EURO_12;
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
        withTaxedProduct(client(), product -> {
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
    public void setLineItemPriceWithoutExternalPrice() {
        withEmptyCartAndProduct(client(), (cart, product) -> {
            assertThat(cart.getLineItems()).hasSize(0);
            final MonetaryAmount externalPrice = EURO_20;
            final AddLineItem action = AddLineItem.of(product.getId(), MASTER_VARIANT_ID, 3L).withExternalPrice(externalPrice);

            final Cart cartWithLineItem = client().executeBlocking(CartUpdateCommand.of(cart, action));
            final LineItem lineItem = cartWithLineItem.getLineItems().get(0);
            assertThat(lineItem.getQuantity()).isEqualTo(3);
            assertThat(lineItem.getPriceMode()).isEqualTo(EXTERNAL_PRICE);
            assertThat(lineItem.getPrice().getValue()).isEqualTo(externalPrice);

            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cartWithLineItem, SetLineItemPrice.of(lineItem.getId())));
            final LineItem updatedLineItem = updatedCart.getLineItems().get(0);
            assertThat(updatedLineItem.getPriceMode()).isEqualTo(PLATFORM);
            assertThat(updatedLineItem.getPrice().getValue()).isEqualTo(MoneyImpl.ofCents(1234, EUR));
        });
    }

    @Test
    public void addLineItemWithExternalPrice() {
        withEmptyCartAndProduct(client(), (cart, product) -> {
            assertThat(cart.getLineItems()).isEmpty();
            final int quantity = 5;
            final int variantId = 1;
            final String productId = product.getId();
            final MonetaryAmount externalPrice = EURO_30;
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
        withTaxedProduct(client(), product -> {
            final int quantity = 5;
            final int variantId = 1;
            final MonetaryAmount initialExternalPrice = EURO_20;
            final LineItemDraft lineItemDraft = LineItemDraft.of(product, variantId, quantity).withExternalPrice(initialExternalPrice);
            withCartWithLineItems(client(), singletonList(lineItemDraft), cart -> {
                final LineItem cartLineItem = cart.getLineItems().get(0);
                assertThat(cartLineItem.getQuantity()).isEqualTo(quantity);

                final MonetaryAmount newExternalPrice = EURO_25;
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
        withTaxedProduct(client(), product -> {
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
        withTaxedProduct(client(), product -> {
            final int quantity = 5;
            final int variantId = 1;
            final MonetaryAmount initialExternalPrice = EURO_20;
            final LineItemDraft lineItemDraft = LineItemDraft.of(product, variantId, quantity).withExternalPrice(initialExternalPrice);
            withCartWithLineItems(client(), singletonList(lineItemDraft), cart -> {
                final LineItem cartLineItem = cart.getLineItems().get(0);
                assertThat(cartLineItem.getQuantity()).isEqualTo(quantity);

                final MonetaryAmount newExternalPrice = EURO_25;
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
        withTaxedProduct(client(), product -> {
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

    @Test
    public void changeLineItemQuantityWithoutExternalTotalPrice() throws Exception {
        withEmptyCartAndProduct(client(), (cart, product) -> {
            assertThat(cart.getLineItems()).hasSize(0);
            final MonetaryAmount price = EURO_1;
            final MonetaryAmount totalPrice = EURO_5;
            final ExternalLineItemTotalPrice externalTotalPrice = ExternalLineItemTotalPrice.ofPriceAndTotalPrice(price, totalPrice);
            final AddLineItem action = AddLineItem.of(product.getId(), MASTER_VARIANT_ID, 3L).withExternalTotalPrice(externalTotalPrice);

            final Cart cartWith3 = client().executeBlocking(CartUpdateCommand.of(cart, action));
            final LineItem lineItem = cartWith3.getLineItems().get(0);
            assertThat(lineItem.getQuantity()).isEqualTo(3);
            assertThat(lineItem.getPriceMode()).isEqualTo(EXTERNAL_TOTAL);
            assertThat(lineItem.getTotalPrice()).isEqualTo(totalPrice);
            assertThat(lineItem.getPrice().getValue()).isEqualTo(price);

            final Cart cartWith2 = client().executeBlocking(CartUpdateCommand.of(cartWith3, ChangeLineItemQuantity.of(lineItem, 2L)));
            final LineItem updatedLineItem = cartWith2.getLineItems().get(0);
            assertThat(updatedLineItem.getQuantity()).isEqualTo(2);
            assertThat(updatedLineItem.getPriceMode()).isEqualTo(PLATFORM);
            assertThat(updatedLineItem.getPrice().getValue()).isEqualTo(MoneyImpl.ofCents(1234, EUR));
        });
    }

}
