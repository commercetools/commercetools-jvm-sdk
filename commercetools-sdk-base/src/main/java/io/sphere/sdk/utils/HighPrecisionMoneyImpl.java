package io.sphere.sdk.utils;

import io.sphere.sdk.models.Base;
import org.javamoney.moneta.Money;

import javax.money.*;
import java.math.BigDecimal;
import java.util.Optional;


/**
 * A variant of {@link MoneyImpl}, this class can be used when the user intends to pass to high precision
 * price, the fractions taken into account can be specified in fractions digits, and it allows to fractionDigits up or down
 * the platform precision for a particular price.
 */
public final class HighPrecisionMoneyImpl extends Base implements MonetaryAmount {
    private final MonetaryAmount money;
    private final int fractionDigits;

    private HighPrecisionMoneyImpl(final MonetaryAmount money, final int fractionsDigit) {
        this.money = money;
        this.fractionDigits = fractionsDigit;
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

    private Money asMoney() {
        return Money.of(getNumber(), getCurrency());
    }

    @Override
    public String toString() {
        return money.toString();
    }

    private static MonetaryAmount of(final MonetaryAmount money) {
        return HighPrecisionMoneyImpl.of(money,money.getCurrency().getDefaultFractionDigits());
    }

    public static MonetaryAmount of(final MonetaryAmount money, int fractionDigits) {
        return new HighPrecisionMoneyImpl(money,fractionDigits);
    }

    public static MonetaryAmount of(final BigDecimal money,CurrencyUnit currency, final int fractionDigits ) {
        return of( Money.of(money,currency),fractionDigits);
    }


    public static MonetaryAmount of(final BigDecimal money,String currency, final int fractionDigits ) {
        return of( money,createCurrencyByCode(currency),fractionDigits);
    }

    public int getFractionDigits() {
        return fractionDigits;
    }

    public static CurrencyUnit createCurrencyByCode(final String currencyCode) {
        CurrencyQuery query = CurrencyQueryBuilder.of().setCurrencyCodes(currencyCode).build();
        return CurrencyUtils.CURRENCY_PROVIDER.getCurrencies(query).stream().findFirst().orElseThrow(() -> new UnknownCurrencyException(currencyCode));
    }
}
