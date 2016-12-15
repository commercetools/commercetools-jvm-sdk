package io.sphere.sdk.products;

import io.sphere.sdk.carts.TaxedItemPrice;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.utils.MoneyImpl;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

/**
 * Tools to support different operations on prices.
 */
public final class PriceUtils {

    private PriceUtils() {
    }

    public static MonetaryAmount zeroAmount(final CurrencyUnit currency) {
        return MoneyImpl.of(0, currency);
    }

    /**
     * Calculates the taxes applied to the pricing.
     * @param taxedPrice the taxed price
     * @return the monetary amount representing the taxes
     */
    public static MonetaryAmount calculateAppliedTaxes(final TaxedItemPrice taxedPrice) {
        return taxedPrice.getTotalGross().subtract(taxedPrice.getTotalNet());
    }

    /**
     * Calculates the gross price of the given amount according to its tax rate.
     * Whether the provided amount is already gross or net is determined by the tax rate.
     * @param amount the monetary amount
     * @param taxRate the tax rate corresponding to the {@code amount}
     * @return the gross monetary amount
     */
    public static MonetaryAmount calculateGrossPrice(final MonetaryAmount amount, final TaxRate taxRate) {
        return taxRate.isIncludedInPrice() ? amount : convertNetToGrossPrice(amount, taxRate.getAmount());
    }

    /**
     * Calculates the net price of the given amount according to its tax rate.
     * Whether the provided amount is already net or gross is determined by the tax rate.
     * @param amount the monetary amount
     * @param taxRate the tax rate corresponding to the {@code amount}
     * @return the net monetary amount
     */
    public static MonetaryAmount calculateNetPrice(final MonetaryAmount amount, final TaxRate taxRate) {
        return taxRate.isIncludedInPrice() ? convertGrossToNetPrice(amount, taxRate.getAmount()) : amount;
    }

    /**
     * Converts the given net amount (i.e. without taxes) to gross (i.e. with taxes included) according to the provided tax rate.
     * @param netAmount the net monetary amount
     * @param taxRate the given tax rate, e.g. {@code 0.19} for 19% tax
     * @return the gross monetary amount
     */
    public static MonetaryAmount convertNetToGrossPrice(final MonetaryAmount netAmount, final double taxRate) {
        return netAmount.multiply(1 + taxRate);
    }

    /**
     * Converts the given gross amount (i.e. with taxes included) to net (i.e. without taxes) according to the provided tax rate.
     * @param grossAmount the gross monetary amount
     * @param taxRate the given tax rate, e.g. {@code 0.19} for 19% tax
     * @return the net monetary amount
     */
    public static MonetaryAmount convertGrossToNetPrice(final MonetaryAmount grossAmount, final double taxRate) {
        return grossAmount.divide(1 + taxRate);
    }
}
