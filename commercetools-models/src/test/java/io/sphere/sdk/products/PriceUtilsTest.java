package io.sphere.sdk.products;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.TaxedItemPrice;
import io.sphere.sdk.taxcategories.SubRate;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.List;

import static io.sphere.sdk.products.PriceUtils.*;
import static io.sphere.sdk.test.SphereTestUtils.EUR;
import static org.assertj.core.api.Assertions.assertThat;

public class PriceUtilsTest {

    @Test
    public void returnsZeroAmount() throws Exception {
        final MonetaryAmount zeroAmount = zeroAmount(EUR);
        assertThat(zeroAmount.getNumber().doubleValue()).isEqualTo(0);
        assertThat(zeroAmount.getCurrency()).isEqualTo(EUR);
    }

    @Test
    public void convertsToGross() throws Exception {
        final MonetaryAmount netAmount = monetaryAmountOf(100);
        final double taxRate = 0.19;
        assertThat(convertNetToGrossPrice(netAmount, taxRate)).isEqualTo(monetaryAmountOf(119));
    }

    @Test
    public void convertsToGrossWithoutRounding() throws Exception {
        final MonetaryAmount netAmount = monetaryAmountOf(3.5);
        final double taxRate = 0.19;
        assertThat(convertNetToGrossPrice(netAmount, taxRate)).isEqualTo(monetaryAmountOf(4.165));
    }

    @Test
    public void convertsToNet() throws Exception {
        final MonetaryAmount grossAmount = monetaryAmountOf(119);
        final double taxRate = 0.19;
        assertThat(convertGrossToNetPrice(grossAmount, taxRate)).isEqualTo(monetaryAmountOf(100));
    }

    @Test
    public void convertsToNetWithoutRounding() throws Exception {
        final MonetaryAmount grossAmount = monetaryAmountOf(3.5);
        final double taxRate = 0.19;
        assertThat(convertGrossToNetPrice(grossAmount, taxRate)).isEqualTo(monetaryAmountOf(2.94118));
    }


    @Test
    public void calculatesGrossWhenTaxesIncluded() throws Exception {
        final TaxRate taxRate = taxRateOf(0.19, true);
        final MonetaryAmount amount = monetaryAmountOf(100);
        assertThat(calculateGrossPrice(amount, taxRate)).isEqualTo(monetaryAmountOf(100));
    }

    @Test
    public void calculatesGrossWhenTaxesNotIncluded() throws Exception {
        final TaxRate taxRate = taxRateOf(0.19, false);
        final MonetaryAmount amount = monetaryAmountOf(100);
        assertThat(calculateGrossPrice(amount, taxRate)).isEqualTo(monetaryAmountOf(119));
    }

    @Test
    public void calculatesNetWhenTaxesIncluded() throws Exception {
        final TaxRate taxRate = taxRateOf(0.19, true);
        final MonetaryAmount amount = monetaryAmountOf(119);
        assertThat(calculateNetPrice(amount, taxRate)).isEqualTo(monetaryAmountOf(100));
    }

    @Test
    public void calculatesNetWhenTaxesNotIncluded() throws Exception {
        final TaxRate taxRate = taxRateOf(0.19, false);
        final MonetaryAmount amount = monetaryAmountOf(119);
        assertThat(calculateNetPrice(amount, taxRate)).isEqualTo(monetaryAmountOf(119));
    }

    @Test
    public void calculatesAppliedTaxes() throws Exception {
        final MonetaryAmount totalNet = monetaryAmountOf(100);
        final MonetaryAmount totalGross = monetaryAmountOf(119);
        assertThat(calculateAppliedTaxes(taxedPriceOf(totalNet, totalGross))).isEqualTo(monetaryAmountOf(19));
    }

    private static MonetaryAmount monetaryAmountOf(final double amount) {
        return MoneyImpl.of(BigDecimal.valueOf(amount), EUR);
    }

    private static TaxRate taxRateOf(final double taxRateAmount, final boolean isIncludedInPrice) {
        return new TaxRate() {
            @Nullable
            @Override
            public String getId() {
                throw new UnsupportedOperationException();
            }

            @Override
            public String getName() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Double getAmount() {
                return taxRateAmount;
            }

            @Override
            public Boolean isIncludedInPrice() {
                return isIncludedInPrice;
            }

            @Override
            public CountryCode getCountry() {
                throw new UnsupportedOperationException();
            }

            @Nullable
            @Override
            public String getState() {
                throw new UnsupportedOperationException();
            }

            @Nonnull
            @Override
            public List<SubRate> getSubRates() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private static TaxedItemPrice taxedPriceOf(final MonetaryAmount totalNet, final MonetaryAmount totalGross) {
        return new TaxedItemPrice() {
            @Override
            public MonetaryAmount getTotalNet() {
                return totalNet;
            }

            @Override
            public MonetaryAmount getTotalGross() {
                return totalGross;
            }
        };
    }
}
