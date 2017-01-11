package io.sphere.sdk.carts;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.ChangeTaxRoundingMode;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.AddPrice;
import io.sphere.sdk.products.commands.updateactions.Publish;
import io.sphere.sdk.products.commands.updateactions.SetTaxCategory;
import io.sphere.sdk.taxcategories.TaxCategoryDraft;
import io.sphere.sdk.taxcategories.TaxRateDraft;
import io.sphere.sdk.taxcategories.TaxRateDraftBuilder;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

import static io.sphere.sdk.carts.CartFixtures.withCartDraft;
import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTaxCategory;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.math.RoundingMode.HALF_EVEN;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class TaxRoundingModeIntegrationTest extends IntegrationTest {

    @Test
    public void cartTaxRoundingModeWithTaxesIncludedInPrice() {
        withPricedProductWithTaxesIncluded(client(), 0.14, 0.12, product -> {
            withTaxedCartWithProduct(client(), product, cartWithDefaultRounding -> {
                assertThat(cartWithDefaultRounding.getTaxRoundingMode()).isEqualTo(RoundingMode.HALF_EVEN);
                assertThat(extractTaxAmount(cartWithDefaultRounding))
                        .as("Should be rounded half to even")
                        .isEqualTo(0.02);

                final CartUpdateCommand updateToHalfUp = CartUpdateCommand.of(cartWithDefaultRounding, ChangeTaxRoundingMode.of(RoundingMode.HALF_UP));
                final Cart cartWithHalfUpRounding = client().executeBlocking(updateToHalfUp);
                assertThat(cartWithHalfUpRounding.getTaxRoundingMode()).isEqualTo(RoundingMode.HALF_UP);
                assertThat(extractTaxAmount(cartWithHalfUpRounding))
                        .as("Should be rounded half up")
                        .isEqualTo(0.01); // fails 0.02

                final CartUpdateCommand updateToHalfDown = CartUpdateCommand.of(cartWithHalfUpRounding, ChangeTaxRoundingMode.of(RoundingMode.HALF_DOWN));
                final Cart cartWithHalfDownRounding = client().executeBlocking(updateToHalfDown);
                assertThat(cartWithHalfDownRounding.getTaxRoundingMode()).isEqualTo(RoundingMode.HALF_DOWN);
                assertThat(extractTaxAmount(cartWithHalfDownRounding))
                        .as("Should be rounded half down")
                        .isEqualTo(0.02);

                return cartWithHalfDownRounding;
            });
        });
    }

    @Test
    public void cartTaxRoundingModeWithTaxesExcludedFromPrice() {
        withPricedProductWithTaxesExcluded(client(), 0.25, 0.14, product -> {
            withTaxedCartWithProduct(client(), product, cartWithDefaultRounding -> {
                assertThat(cartWithDefaultRounding.getTaxRoundingMode()).isEqualTo(RoundingMode.HALF_EVEN);
                assertThat(extractTaxAmount(cartWithDefaultRounding))
                        .as("Should be rounded half to even")
                        .isEqualTo(0.03); // fails 0.04

                final CartUpdateCommand updateToHalfUp = CartUpdateCommand.of(cartWithDefaultRounding, ChangeTaxRoundingMode.of(RoundingMode.HALF_UP));
                final Cart cartWithHalfUpRounding = client().executeBlocking(updateToHalfUp);
                assertThat(cartWithHalfUpRounding.getTaxRoundingMode()).isEqualTo(RoundingMode.HALF_UP);
                assertThat(extractTaxAmount(cartWithHalfUpRounding))
                        .as("Should be rounded half up")
                        .isEqualTo(0.04);

                final CartUpdateCommand updateToHalfDown = CartUpdateCommand.of(cartWithHalfUpRounding, ChangeTaxRoundingMode.of(RoundingMode.HALF_DOWN));
                final Cart cartWithHalfDownRounding = client().executeBlocking(updateToHalfDown);
                assertThat(cartWithHalfDownRounding.getTaxRoundingMode()).isEqualTo(RoundingMode.HALF_DOWN);
                assertThat(extractTaxAmount(cartWithHalfDownRounding))
                        .as("Should be rounded half down")
                        .isEqualTo(0.03); // fails 0.04

                return cartWithHalfDownRounding;
            });
        });
    }

    // TODO To be deleted

    /**
     * gross -> convert to net -> calculate taxes -> round taxes
     * results: Rounding Tax Amount From Gross
     */
    @Test
    public void findProblemsWithRoundingTaxAmountFromGross() throws Exception {
        IntStream.rangeClosed(1, 100).forEach(grossCentAmount -> {
            IntStream.rangeClosed(0, 100).forEach(taxRatePercentage -> {

                final BigDecimal taxRate = BigDecimal.valueOf(taxRatePercentage).movePointLeft(2);
                final BigDecimal grossAmount = BigDecimal.valueOf(grossCentAmount).movePointLeft(2);
                final BigDecimal netAmount = convertGrossToNetPrice(grossAmount, taxRate);
                final BigDecimal taxAmount = calculateTaxAmount(grossAmount, netAmount);

                final BigDecimal roundedUpTaxAmount = round(taxAmount, java.math.RoundingMode.HALF_UP);
                final BigDecimal roundedEvenTaxAmount = round(taxAmount, HALF_EVEN);
                final BigDecimal roundedDownTaxAmount = round(taxAmount, java.math.RoundingMode.HALF_DOWN);

                if (!roundedUpTaxAmount.equals(roundedEvenTaxAmount) || !roundedEvenTaxAmount.equals(roundedDownTaxAmount)) {
                    System.out.printf("gross: %s, net: %s, tax rate: %s, tax amount: %s, tax amount up: %s, tax amount even: %s, tax amount down: %s\n",
                            grossAmount,
                            netAmount,
                            taxRate,
                            taxAmount,
                            roundedUpTaxAmount,
                            roundedEvenTaxAmount,
                            roundedDownTaxAmount);
                }
            });
        });
    }

    /**
     * net -> convert to gross -> calculate taxes -> round taxes
     * results: Rounding Tax Amount from Net
     */
    @Test
    public void findProblemsWithRoundingTaxAmountFromNet() throws Exception {
        IntStream.rangeClosed(1, 100).forEach(netCentAmount -> {
            IntStream.rangeClosed(0, 100).forEach(taxRatePercentage -> {

                final BigDecimal taxRate = BigDecimal.valueOf(taxRatePercentage).movePointLeft(2);
                final BigDecimal netAmount = BigDecimal.valueOf(netCentAmount).movePointLeft(2);
                final BigDecimal grossAmount = convertNetToGrossPrice(netAmount, taxRate);
                final BigDecimal taxAmount = calculateTaxAmount(grossAmount, netAmount);

                final BigDecimal roundedUpTaxAmount = round(taxAmount, java.math.RoundingMode.HALF_UP);
                final BigDecimal roundedEvenTaxAmount = round(taxAmount, HALF_EVEN);
                final BigDecimal roundedDownTaxAmount = round(taxAmount, java.math.RoundingMode.HALF_DOWN);

                if (!roundedUpTaxAmount.equals(roundedEvenTaxAmount) || !roundedEvenTaxAmount.equals(roundedDownTaxAmount)) {
                    System.out.printf("gross: %s, net: %s, tax rate: %s, tax amount: %s, tax amount up: %s, tax amount even: %s, tax amount down: %s\n",
                            grossAmount,
                            netAmount,
                            taxRate,
                            taxAmount,
                            roundedUpTaxAmount,
                            roundedEvenTaxAmount,
                            roundedDownTaxAmount);
                }
            });
        });
    }

    /**
     * gross -> convert to net -> round net -> calculate taxes
     * results: Rounding Net
     */
    @Test
    public void findProblemsWithRoundingNetAmount() throws Exception {
        IntStream.rangeClosed(1, 100).forEach(grossCentAmount -> {
            IntStream.rangeClosed(0, 100).forEach(taxRatePercentage -> {

                final BigDecimal taxRate = BigDecimal.valueOf(taxRatePercentage).movePointLeft(2);
                final BigDecimal grossAmount = BigDecimal.valueOf(grossCentAmount).movePointLeft(2);
                final BigDecimal netAmount = convertGrossToNetPrice(grossAmount, taxRate);

                final BigDecimal roundedUpNetAmount = round(netAmount, java.math.RoundingMode.HALF_UP);
                final BigDecimal roundedEvenNetAmount = round(netAmount, HALF_EVEN);
                final BigDecimal roundedDownNetAmount = round(netAmount, java.math.RoundingMode.HALF_DOWN);

                final BigDecimal roundedUpTaxAmount = calculateTaxAmount(grossAmount, roundedUpNetAmount);
                final BigDecimal roundedEvenTaxAmount = calculateTaxAmount(grossAmount, roundedEvenNetAmount);
                final BigDecimal roundedDownTaxAmount = calculateTaxAmount(grossAmount, roundedDownNetAmount);

                if (!roundedUpNetAmount.equals(roundedEvenNetAmount) || !roundedEvenNetAmount.equals(roundedDownNetAmount)
                        || !roundedUpTaxAmount.equals(roundedEvenTaxAmount) || !roundedEvenTaxAmount.equals(roundedDownTaxAmount)) {

                    System.out.printf("gross: %s, net: %s, tax rate: %s, net up: %s, net even: %s, net down: %s, tax amount up: %s, tax amount even: %s, tax amount down: %s\n",
                            grossAmount,
                            netAmount,
                            taxRate,
                            roundedUpNetAmount,
                            roundedEvenNetAmount,
                            roundedDownNetAmount,
                            roundedUpTaxAmount,
                            roundedEvenTaxAmount,
                            roundedDownTaxAmount);


                }
            });
        });
    }

    /**
     * net -> convert to gross -> round gross -> calculate taxes
     * results: Rounding Gross
     */
    @Test
    public void findProblemsWithRoundingGrossAmount() throws Exception {
        IntStream.rangeClosed(1, 100).forEach(netCentAmount -> {
            IntStream.rangeClosed(0, 100).forEach(taxRatePercentage -> {

                final BigDecimal taxRate = BigDecimal.valueOf(taxRatePercentage).movePointLeft(2);
                final BigDecimal netAmount = BigDecimal.valueOf(netCentAmount).movePointLeft(2);
                final BigDecimal grossAmount = convertNetToGrossPrice(netAmount, taxRate);

                final BigDecimal roundedUpGrossAmount = round(grossAmount, java.math.RoundingMode.HALF_UP);
                final BigDecimal roundedEvenGrossAmount = round(grossAmount, HALF_EVEN);
                final BigDecimal roundedDownGrossAmount = round(grossAmount, java.math.RoundingMode.HALF_DOWN);

                final BigDecimal roundedUpTaxAmount = calculateTaxAmount(roundedUpGrossAmount, netAmount);
                final BigDecimal roundedEvenTaxAmount = calculateTaxAmount(roundedEvenGrossAmount, netAmount);
                final BigDecimal roundedDownTaxAmount = calculateTaxAmount(roundedDownGrossAmount, netAmount);

                if (!roundedUpGrossAmount.equals(roundedEvenGrossAmount) || !roundedEvenGrossAmount.equals(roundedDownGrossAmount)
                        || !roundedUpTaxAmount.equals(roundedEvenTaxAmount) || !roundedEvenTaxAmount.equals(roundedDownTaxAmount)) {

                    System.out.printf("gross: %s, net: %s, tax rate: %s, gross up: %s, gross even: %s, gross down: %s, tax amount up: %s, tax amount even: %s, tax amount down: %s\n",
                            grossAmount,
                            netAmount,
                            taxRate,
                            roundedUpGrossAmount,
                            roundedEvenGrossAmount,
                            roundedDownGrossAmount,
                            roundedUpTaxAmount,
                            roundedEvenTaxAmount,
                            roundedDownTaxAmount);


                }
            });
        });
    }

    private static BigDecimal convertGrossToNetPrice(final BigDecimal grossAmount, final BigDecimal taxRate) {
        return grossAmount.divide(taxRate.add(BigDecimal.ONE), 8, HALF_EVEN);
    }

    private static BigDecimal convertNetToGrossPrice(final BigDecimal netAmount, final BigDecimal taxRate) {
        return netAmount.multiply(taxRate.add(BigDecimal.ONE));
    }

    private static BigDecimal calculateTaxAmount(final BigDecimal grossAmount, final BigDecimal netAmount) {
        return grossAmount.subtract(netAmount);
    }

    private static BigDecimal round(final BigDecimal value, java.math.RoundingMode roundingMode) {
        return value.setScale(2, roundingMode);
    }

    private static double extractTaxAmount(final Cart cartWithDefaultRounding) {
        return cartWithDefaultRounding.getTaxedPrice().getTaxPortions().get(0).getAmount().getNumber().doubleValueExact();
    }

    private static void withTaxedCartWithProduct(final BlockingSphereClient client, final Product product, final Function<Cart, CartLike<?>> operator) {
        final LineItemDraft lineItemDraft = LineItemDraft.of(product, MASTER_VARIANT_ID, 1);
        final CartDraftDsl cartDraft = CartDraft.of(EUR)
                .withLineItems(singletonList(lineItemDraft))
                .withShippingAddress(Address.of(CountryCode.DE))
                .withCountry(CountryCode.DE);
        withCartDraft(client, cartDraft, operator);
    }

    private static void withPricedProductWithTaxesIncluded(final BlockingSphereClient client, final double price, final double taxRate, final Consumer<Product> operator) {
        final List<TaxRateDraft> taxRateDrafts = singletonList(TaxRateDraftBuilder.of("taxRateInc", taxRate, true, CountryCode.DE).build());
        final TaxCategoryDraft taxCategoryDraft = TaxCategoryDraft.of(randomString(), taxRateDrafts);
        createProduct(client, taxCategoryDraft, price, operator);
    }

    private static void withPricedProductWithTaxesExcluded(final BlockingSphereClient client, final double price, final double taxRate, final Consumer<Product> operator) {
        final List<TaxRateDraft> taxRateDrafts = singletonList(TaxRateDraftBuilder.of("taxRateExc", taxRate, false, CountryCode.DE).build());
        final TaxCategoryDraft taxCategoryDraft = TaxCategoryDraft.of(randomString(), taxRateDrafts);
        createProduct(client, taxCategoryDraft, price, operator);
    }

    private static void createProduct(final BlockingSphereClient client, final TaxCategoryDraft taxCategoryDraft, final double price, final Consumer<Product> operator) {
        withTaxCategory(client, taxCategoryDraft, taxCategory -> {
            withProduct(client, product -> {
                final PriceDraft priceDraft = PriceDraft.of(MoneyImpl.of(new BigDecimal(String.valueOf(price)), EUR)).withCountry(DE);
                final ProductUpdateCommand setPricesCmd = ProductUpdateCommand.of(product, asList(
                        AddPrice.of(MASTER_VARIANT_ID, priceDraft),
                        SetTaxCategory.of(taxCategory),
                        Publish.of()));
                final Product productWithPrice = client.executeBlocking(setPricesCmd);
                operator.accept(productWithPrice);
            });
        });
    }
}
