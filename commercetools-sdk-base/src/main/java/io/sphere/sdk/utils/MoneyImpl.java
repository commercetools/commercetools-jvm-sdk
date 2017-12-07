package io.sphere.sdk.utils;

import com.neovisionaries.i18n.CurrencyCode;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.DefaultCurrencyUnits;
import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.function.MonetaryQueries;

import javax.annotation.Nonnull;
import javax.money.*;
import javax.money.format.MonetaryFormats;
import java.math.BigDecimal;
import java.util.Optional;

public final class MoneyImpl extends Base implements MonetaryAmount {
    private final MonetaryAmount money;

    private static volatile boolean initialized = false;

    private MoneyImpl(final MonetaryAmount money) {
        this.money = money;

        //fixes https://github.com/commercetools/commercetools-sunrise-java/issues/404
        //exception play.api.http.HttpErrorHandlerExceptions$$anon$1: Execution exception[[CompletionException: java.lang.IllegalArgumentException: java.util.concurrent.CompletionException: io.sphere.sdk.json.JsonException: detailMessage: com.fasterxml.jackson.databind.JsonMappingException: Operator failed: javax.money.DefaultMonetaryRoundingsSingletonSpi$DefaultCurrencyRounding@1655879e (through reference chain: io.sphere.sdk.payments.PaymentDraftImpl["amountPlanned"])
        CurrencyUnit currencyUnit = DefaultCurrencyUnits.EUR;
        if(!initialized){
            try{
                Monetary.getDefaultRounding();
                Monetary.getDefaultAmountType();
                MonetaryFormats.getDefaultFormatProviderChain();
                Monetary.getDefaultCurrencyProviderChain();
                initialized = true;
            }catch(Exception e){

            }
        }

    }

    @Override
    public MonetaryContext getContext() {
        return money.getContext();
    }

    @Override
    public <R> R query(MonetaryQuery<R> query) {
        return money.query(query);
    }

    @Override
    public MonetaryAmount with(MonetaryOperator operator) {
        return of(money.with(operator));
    }

    @Override
    public MonetaryAmountFactory<? extends MonetaryAmount> getFactory() {
        return money.getFactory();
    }

    @Override
    public boolean isGreaterThan(MonetaryAmount amount) {
        return money.isGreaterThan(amount);
    }

    @Override
    public boolean isGreaterThanOrEqualTo(MonetaryAmount amount) {
        return money.isGreaterThanOrEqualTo(amount);
    }

    @Override
    public boolean isLessThan(MonetaryAmount amount) {
        return money.isLessThan(amount);
    }

    @Override
    public boolean isLessThanOrEqualTo(MonetaryAmount amt) {
        return money.isLessThanOrEqualTo(amt);
    }

    @Override
    public boolean isEqualTo(MonetaryAmount amount) {
        return money.isEqualTo(amount);
    }

    @Override
    public boolean isNegative() {
        return money.isNegative();
    }

    @Override
    public boolean isNegativeOrZero() {
        return money.isNegativeOrZero();
    }

    @Override
    public boolean isPositive() {
        return money.isPositive();
    }

    @Override
    public boolean isPositiveOrZero() {
        return money.isPositiveOrZero();
    }

    @Override
    public boolean isZero() {
        return money.isZero();
    }

    @Override
    public int signum() {
        return money.signum();
    }

    @Override
    public MonetaryAmount add(MonetaryAmount amount) {
        return of(money.add(amount));
    }

    @Override
    public MonetaryAmount subtract(MonetaryAmount amount) {
        return of(money.subtract(amount));
    }

    @Override
    public MonetaryAmount multiply(long multiplicand) {
        return of(money.multiply(multiplicand));
    }

    @Override
    public MonetaryAmount multiply(double multiplicand) {
        return of(money.multiply(multiplicand));
    }

    @Override
    public MonetaryAmount multiply(Number multiplicand) {
        return of(money.multiply(multiplicand));
    }

    @Override
    public MonetaryAmount divide(long divisor) {
        return of(money.divide(divisor));
    }

    @Override
    public MonetaryAmount divide(double divisor) {
        return of(money.divide(divisor));
    }

    @Override
    public MonetaryAmount divide(Number divisor) {
        return of(money.divide(divisor));
    }

    @Override
    public MonetaryAmount remainder(long divisor) {
        return of(money.remainder(divisor));
    }

    @Override
    public MonetaryAmount remainder(double divisor) {
        return of(money.remainder(divisor));
    }

    @Override
    public MonetaryAmount remainder(Number divisor) {
        return of(money.remainder(divisor));
    }

    private static MonetaryAmount[] of(final MonetaryAmount[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = of(array[i]);
        }
        return array;
    }

    @Override
    public MonetaryAmount[] divideAndRemainder(long divisor) {
        return of(money.divideAndRemainder(divisor));
    }

    @Override
    public MonetaryAmount[] divideAndRemainder(double divisor) {
        return of(money.divideAndRemainder(divisor));
    }

    @Override
    public MonetaryAmount[] divideAndRemainder(Number divisor) {
        return of(money.divideAndRemainder(divisor));
    }

    @Override
    public MonetaryAmount divideToIntegralValue(long divisor) {
        return of(money.divideToIntegralValue(divisor));
    }

    @Override
    public MonetaryAmount divideToIntegralValue(double divisor) {
        return of(money.divideToIntegralValue(divisor));
    }

    @Override
    public MonetaryAmount divideToIntegralValue(Number divisor) {
        return of(money.divideToIntegralValue(divisor));
    }

    @Override
    public MonetaryAmount scaleByPowerOfTen(int power) {
        return of(money.scaleByPowerOfTen(power));
    }

    @Override
    public MonetaryAmount abs() {
        return of(money.abs());
    }

    @Override
    public MonetaryAmount negate() {
        return of(money.negate());
    }

    @Override
    public MonetaryAmount plus() {
        return of(money.plus());
    }

    @Override
    public MonetaryAmount stripTrailingZeros() {
        return of(money.stripTrailingZeros());
    }

    @Override
    public CurrencyUnit getCurrency() {
        return money.getCurrency();
    }

    @Override
    public NumberValue getNumber() {
        return money.getNumber();
    }

    @Override
    public int compareTo(MonetaryAmount o) {
        return money.compareTo(o);
    }

    @Override
    public boolean equals(final Object obj) {
        return Optional.ofNullable(obj)
                .filter(other -> other != null)
                .filter(other -> other instanceof MonetaryAmount)
                .map(o -> (MonetaryAmount) o)
                .map(o -> o.getCurrency().equals(getCurrency()) && o.isEqualTo(money))
                .orElse(false);
    }

    @Override
    public int hashCode() {
        return asMoney().hashCode();
    }

    @Override
    public String toString() {
        return money.toString();
    }

    public static MonetaryAmount of(final MonetaryAmount money) {
        return new MoneyImpl(money);
    }

    public static MonetaryAmount of(BigDecimal amount, CurrencyUnit currency) {
        return of(FastMoney.of(amount, currency));
    }

    private FastMoney asMoney() {
        return FastMoney.of(getNumber(), getCurrency());
    }

    public static MonetaryAmount of(final int amount, final CurrencyUnit currencyUnit) {
        return of(new BigDecimal(amount), currencyUnit);
    }

    public static MonetaryAmount of(final BigDecimal amount, final String currencyCode) {
        final CurrencyUnit currency = createCurrencyByCode(currencyCode);
        return MoneyImpl.of(amount, currency);
    }

    private static CurrencyUnit createCurrencyByCode(final String currencyCode) {
        return Monetary.getCurrency(currencyCode);
    }

    public static MonetaryAmount of(final String amount, final CurrencyUnit currencyUnit) {
        return of(new BigDecimal(amount), currencyUnit);
    }

    /**
     * Creates a {@link MonetaryAmount} from a cent amount as String and a currencyUnit as String.
     *
     * @param amount the amount in cents which consists just of numbers
     * @param currencyCode the currency code as String like "EUR" for EURO
     * @return amount
     */
    public static MonetaryAmount ofCentsAndCurrencyCode(final String amount, final String currencyCode) {
        return ofCents(Long.parseLong(amount), currencyCode);
    }

    public static MonetaryAmount ofCents(final long centAmount, final String currencyCode) {
        return ofCents(centAmount, createCurrencyByCode(currencyCode));
    }

    public static MonetaryAmount ofCents(final long centAmount, final CurrencyUnit currencyUnit) {
        return of(new BigDecimal(centAmount).divide(new BigDecimal(10).pow(currencyUnit.getDefaultFractionDigits())), currencyUnit);
    }

    public static MonetaryAmount of(final String value, final String currencyCode) {
        return of(new BigDecimal(value), createCurrencyByCode(currencyCode));
    }

    public static Long centAmountOf(@Nonnull final MonetaryAmount monetaryAmount) {
        return monetaryAmount.query(MonetaryQueries.convertMinorPart());
    }
}
