package io.sphere.sdk.carts;

import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.AddLineItem;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.taxcategories.*;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.products.ProductFixtures.withProductOfPrices;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class ExternalTaxRatesIntegrationTest extends IntegrationTest {

    @Test
    public void createEmptyCart() {
        final CartDraftDsl cartDraft = CartDraft.of(EUR)
                .withTaxMode(TaxMode.EXTERNAL);//important

        final Cart cart = client().executeBlocking(CartCreateCommand.of(cartDraft));

        assertThat(cart.getTaxMode()).isEqualTo(TaxMode.EXTERNAL);
    }

    @Test
    public void addLineItem() {
        withProductOfPrices(client(), singletonList(PriceDraft.of(EURO_10)), (Product product) -> {
            final CartDraft draft = CartDraft.of(EUR).withTaxMode(TaxMode.EXTERNAL);
            CartFixtures.withCartDraft(client(), draft, (Cart cart) -> {
                final int quantity = 3;
                final int variantId = product.getMasterData().getStaged().getMasterVariant().getId();
                final String taxRateName = "special tax";
                final double amount = 0.20;
                final List<SubRate> subRates = asList(SubRate.of("foo", 0.11), SubRate.of("bar", 0.09));
                final ExternalTaxRateDraft externalTaxRate =
                        ExternalTaxRateDraftBuilder.ofAmount(amount, taxRateName, DE)
                                .subRates(subRates)
                                .build();
                final AddLineItem updateAction = AddLineItem.of(product, variantId, quantity)
                        .withExternalTaxRate(externalTaxRate);
                final Cart cartWithLineItem = client().executeBlocking(CartUpdateCommand.of(cart, updateAction));

                final LineItem lineItem = cartWithLineItem.getLineItems().get(0);
                final TaxRate taxRate = lineItem.getTaxRate();
                assertThat(taxRate.getSubRates()).isEqualTo(subRates);
                assertThat(taxRate.getCountry()).isEqualTo(DE);
                assertThat(taxRate.getId()).isNull();
                assertThat(taxRate.getName()).isEqualTo(taxRateName);
                assertThat(taxRate.getAmount()).isEqualTo(0.2);
                assertThat(taxRate.isIncludedInPrice()).isFalse();
                assertThat(lineItem.getTotalPrice()).isEqualTo(MoneyImpl.ofCents(3000, "EUR"));
                assertThat(lineItem.getTaxedPrice().getTotalNet()).isEqualTo(MoneyImpl.ofCents(3000, "EUR"));
                assertThat(lineItem.getTaxedPrice().getTotalGross()).isEqualTo(MoneyImpl.ofCents(3600, "EUR"));

                return cartWithLineItem;
            });
        });
    }

    @Test
    public void createCartWithALineItemWithAnExternalTaxRate() {
        withProductOfPrices(client(), singletonList(PriceDraft.of(EURO_10)), (Product product) -> {
            final String taxRateName = "special tax";
            final double amount = 0.20;
            final ExternalTaxRateDraft externalTaxRate =
                    ExternalTaxRateDraftBuilder.ofAmount(amount, taxRateName, DE).build();
            final int quantity = 22;
            final int variantId = 1;
            final LineItemDraft itemDraft = LineItemDraft.of(product, variantId, quantity)
                    .withExternalTaxRate(externalTaxRate);
            final CartDraftDsl cartDraft = CartDraft.of(EUR)
                    .withTaxMode(TaxMode.EXTERNAL)//important
                    .withCountry(DE)
                    .withShippingAddress(Address.of(DE))
                    .withLineItems(singletonList(itemDraft));
            final CartCreateCommand cmd = CartCreateCommand.of(cartDraft);

            final Cart cart = client().executeBlocking(cmd);

            assertThat(cart.getTaxMode()).isEqualTo(TaxMode.EXTERNAL);
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
