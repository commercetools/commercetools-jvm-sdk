package io.sphere.sdk.carts;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.*;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.AddPrice;
import io.sphere.sdk.products.commands.updateactions.Publish;
import io.sphere.sdk.products.commands.updateactions.SetTaxCategory;
import io.sphere.sdk.taxcategories.*;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.carts.CartFixtures.withCartDraft;
import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.products.PriceUtils.convertGrossToNetPrice;
import static io.sphere.sdk.products.ProductFixtures.*;
import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTaxCategory;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class TaxRoundingModeIntegrationTest extends IntegrationTest {

    @Test
    public void cartTaxRoundingMode() {
        withTaxedCartWithProduct(client(), cartWithDefaultRounding -> {
            assertThat(cartWithDefaultRounding.getTaxRoundingMode()).isEqualTo(RoundingMode.HALF_EVEN);
            assertThat(extractTaxAmount(cartWithDefaultRounding))
                    .as("Should be rounded half to even")
                    .isEqualTo(2.22);

            final CartUpdateCommand updateToHalfUp = CartUpdateCommand.of(cartWithDefaultRounding, ChangeTaxRoundingMode.of(RoundingMode.HALF_UP));
            final Cart cartWithHalfUpRounding = client().executeBlocking(updateToHalfUp);
            assertThat(cartWithHalfUpRounding.getTaxRoundingMode()).isEqualTo(RoundingMode.HALF_UP);
            assertThat(extractTaxAmount(cartWithHalfUpRounding))
                    .as("Should be rounded half up")
                    .isEqualTo(2.23);

            final CartUpdateCommand updateToHalfDown = CartUpdateCommand.of(cartWithHalfUpRounding, ChangeTaxRoundingMode.of(RoundingMode.HALF_DOWN));
            final Cart cartWithHalfDownRounding = client().executeBlocking(updateToHalfDown);
            assertThat(cartWithHalfDownRounding.getTaxRoundingMode()).isEqualTo(RoundingMode.HALF_DOWN);
            assertThat(extractTaxAmount(cartWithHalfDownRounding))
                    .as("Should be rounded half down")
                    .isEqualTo(2.22);

            return cartWithHalfDownRounding;
        });
    }

    // TODO To be deleted
    @Test
    public void checkNotRoundedTaxAmount() throws Exception {
        final MonetaryAmount grossAmount = monetaryAmountOf(12.34);
        final double taxRate = 0.22;
        final MonetaryAmount netAmount = convertGrossToNetPrice(grossAmount, taxRate);
        assertThat(grossAmount.subtract(netAmount)).isEqualTo(monetaryAmountOf(2.22525));
    }


    private static MonetaryAmount monetaryAmountOf(final double amount) {
        return MoneyImpl.of(BigDecimal.valueOf(amount), SphereTestUtils.EUR);
    }

    private static double extractTaxAmount(final Cart cartWithDefaultRounding) {
        return cartWithDefaultRounding.getTaxedPrice().getTaxPortions().get(0).getAmount().getNumber().doubleValueExact();
    }

    private static void withTaxedCartWithProduct(final BlockingSphereClient client, final Function<Cart, CartLike<?>> operator) {
        withTaxedAndPricedProduct(client, product -> {
            final LineItemDraft lineItemDraft = LineItemDraft.of(product, MASTER_VARIANT_ID, 1);
            final CartDraftDsl cartDraft = CartDraft.of(EUR)
                    .withLineItems(singletonList(lineItemDraft))
                    .withShippingAddress(Address.of(CountryCode.DE))
                    .withCountry(CountryCode.DE);
            withCartDraft(client, cartDraft, operator);
        });
    }

    private static void withTaxedAndPricedProduct(final BlockingSphereClient client, final Consumer<Product> operator) {
        final List<TaxRateDraft> taxRateDrafts = singletonList(TaxRateDraftBuilder.of("de22", 0.22, true, CountryCode.DE).build());
        final TaxCategoryDraft taxCategoryDraft = TaxCategoryDraft.of(randomString(), taxRateDrafts);
        withTaxCategory(client, taxCategoryDraft, taxCategory -> {
            withProduct(client, product -> {
                final ProductUpdateCommand setPricesCmd = ProductUpdateCommand.of(product, asList(
                        AddPrice.of(MASTER_VARIANT_ID, PRICE),
                        SetTaxCategory.of(taxCategory),
                        Publish.of()));
                final Product productWithPrice = client.executeBlocking(setPricesCmd);
                operator.accept(productWithPrice);
            });
        });
    }
}
