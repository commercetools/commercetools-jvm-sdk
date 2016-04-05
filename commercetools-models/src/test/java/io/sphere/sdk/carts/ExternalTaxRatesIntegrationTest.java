package io.sphere.sdk.carts;

import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.AddLineItem;
import io.sphere.sdk.carts.queries.CartByIdGet;
import io.sphere.sdk.client.JsonNodeSphereRequest;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductFixtures;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraftBuilder;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import java.util.Collections;

import static io.sphere.sdk.carts.CartFixtures.withCartAndTaxedProduct;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class ExternalTaxRatesIntegrationTest extends IntegrationTest {
//    @Test
//    public void addLineItemWithExternalTaxRateToACart() {
//        withCartAndTaxedProduct(client(), (Cart cart, Product product) -> {
//            CartUpdateCommand.of(cart, AddLineItem.)//?????
//        });
//
//    }


    @Test
    public void createCartWithALineItemWithAnExternalTaxRate() {
        ProductFixtures.withProductOfPrices(client(), singletonList(PriceDraft.of(EURO_10)),(Product product) -> {
            final String taxRateName = "special tax";
            final double amount = 0.20;
            final ExternalTaxRateDraft externalTaxRate =
                    ExternalTaxRateDraftBuilder.ofAmount(amount, taxRateName, DE).build();
            final LineItemDraft itemDraft = LineItemDraft.of(product, 1, 22)
                    .withExternalTaxRate(externalTaxRate);
            final CartDraftDsl cartDraft = CartDraft.of(EUR)
                    .withCountry(DE)
                    .withShippingAddress(Address.of(DE))
                    .withLineItems(singletonList(itemDraft));
            final CartCreateCommand cmd = CartCreateCommand.of(cartDraft);

            final Cart cart = client().executeBlocking(cmd);

            final LineItem lineItem = cart.getLineItems().get(0);

            softAssert(s -> {
                final TaxRate taxRate = lineItem.getTaxRate();
                s.assertThat(taxRate.getName()).isEqualTo(taxRateName);
                s.assertThat(taxRate.getAmount()).isEqualTo(amount);
                s.assertThat(taxRate.getCountry()).isEqualTo(DE);
                s.assertThat(taxRate.getId()).as("tax rate id").isNull();
                s.assertThat(taxRate.getSubRates()).as("sub rates").isEmpty();
                s.assertThat(lineItem.getTotalPrice()).as("lineItem totalPrice").isEqualTo(MoneyImpl.of(220, EUR));
                s.assertThat(cart.getTotalPrice()).as("cart totalPrice").isEqualTo(MoneyImpl.of(220, EUR));
                final TaxedPrice taxedPrice = cart.getTaxedPrice();
                System.err.println("taxedPrice " + taxedPrice);
                System.err.println(cart);
                s.assertThat(taxedPrice.getTotalGross()).as("gross").isEqualTo(MoneyImpl.of(264, EUR));
                s.assertThat(taxedPrice.getTotalNet()).as("net").isEqualTo(MoneyImpl.of(220, EUR));
            });
        });

    }

    //TODO check with subRates
    //TODO check with states
    //test io.sphere.sdk.taxcategories.TaxRate.getSubRates() not null
    //remove external tag rate from custom line item without taxcat
}
