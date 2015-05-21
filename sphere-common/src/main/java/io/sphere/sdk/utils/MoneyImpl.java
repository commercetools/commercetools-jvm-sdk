package io.sphere.sdk.utils;

import io.sphere.sdk.models.Base;
import org.javamoney.moneta.CurrencyUnitBuilder;
import org.javamoney.moneta.FastMoney;

import javax.money.*;
import java.math.BigDecimal;
import java.util.Optional;

final public class MoneyImpl extends Base implements MonetaryAmount {
    private final MonetaryAmount money;

    private MoneyImpl(final MonetaryAmount money) {
        this.money = money;
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
        return money.with(operator);
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
        return money.add(amount);
    }

    @Override
    public MonetaryAmount subtract(MonetaryAmount amount) {
        return money.subtract(amount);
    }

    @Override
    public MonetaryAmount multiply(long multiplicand) {
        return money.multiply(multiplicand);
    }

    @Override
    public MonetaryAmount multiply(double multiplicand) {
        return money.multiply(multiplicand);
    }

    @Override
    public MonetaryAmount multiply(Number multiplicand) {
        return money.multiply(multiplicand);
    }

    @Override
    public MonetaryAmount divide(long divisor) {
        return money.divide(divisor);
    }

    @Override
    public MonetaryAmount divide(double divisor) {
        return money.divide(divisor);
    }

    @Override
    public MonetaryAmount divide(Number divisor) {
        return money.divide(divisor);
    }

    @Override
    public MonetaryAmount remainder(long divisor) {
        return money.remainder(divisor);
    }

    @Override
    public MonetaryAmount remainder(double divisor) {
        return money.remainder(divisor);
    }

    @Override
    public MonetaryAmount remainder(Number divisor) {
        return money.remainder(divisor);
    }

    @Override
    public MonetaryAmount[] divideAndRemainder(long divisor) {
        return money.divideAndRemainder(divisor);
    }

    @Override
    public MonetaryAmount[] divideAndRemainder(double divisor) {
        return money.divideAndRemainder(divisor);
    }

    @Override
    public MonetaryAmount[] divideAndRemainder(Number divisor) {
        return money.divideAndRemainder(divisor);
    }

    @Override
    public MonetaryAmount divideToIntegralValue(long divisor) {
        return money.divideToIntegralValue(divisor);
    }

    @Override
    public MonetaryAmount divideToIntegralValue(double divisor) {
        return money.divideToIntegralValue(divisor);
    }

    @Override
    public MonetaryAmount divideToIntegralValue(Number divisor) {
        return money.divideToIntegralValue(divisor);
    }

    @Override
    public MonetaryAmount scaleByPowerOfTen(int power) {
        return money.scaleByPowerOfTen(power);
    }

    @Override
    public MonetaryAmount abs() {
        return money.abs();
    }

    @Override
    public MonetaryAmount negate() {
        return money.negate();
    }

    @Override
    public MonetaryAmount plus() {
        return money.plus();
    }

    @Override
    public MonetaryAmount stripTrailingZeros() {
        return money.stripTrailingZeros();
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
                .map(o -> o.isEqualTo(money))
                .orElse(false);
    }

    @Override
    public int hashCode() {
        return asMoney().hashCode();
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
        final CurrencyUnit currency = CurrencyUnitBuilder.of(currencyCode, CurrencyContextBuilder.of("default").build()).build();
        return MoneyImpl.of(amount, currency);
    }

    public static MonetaryAmount of(final String amount, final CurrencyUnit currencyUnit) {
        return of(new BigDecimal(amount), currencyUnit);
    }
}
