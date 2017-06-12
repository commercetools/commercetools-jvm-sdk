package io.sphere.sdk.carts;

import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.test.IntegrationTest;
import org.javamoney.moneta.Money;
import org.junit.Test;

import static io.sphere.sdk.carts.LineItemPriceMode.EXTERNAL_PRICE;
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
            final CartDraftDsl cartDraft = CartDraft.of(EUR)
                    .withTaxMode(TaxMode.EXTERNAL)
                    .withCountry(DE)
                    .withShippingAddress(Address.of(DE))
                    .withLineItems(singletonList(lineItemDraft));
            final CartCreateCommand cmd = CartCreateCommand.of(cartDraft);
            final Cart cart = client().executeBlocking(cmd);
            final LineItem cartLineItem = cart.getLineItems().get(0);
            assertThat(cartLineItem.getPrice().getValue()).isEqualTo(externalPrice);
            assertThat(cartLineItem.getPriceMode()).isEqualTo(EXTERNAL_PRICE);
        });
    }
}
