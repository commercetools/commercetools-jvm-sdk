package io.sphere.sdk.carts;

import io.sphere.sdk.test.IntegrationTest;
import org.javamoney.moneta.Money;
import org.junit.Test;

import javax.money.MonetaryAmount;

import static io.sphere.sdk.carts.CartFixtures.withCartWithLineItems;
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
            });
        });
    }

}
