package io.sphere.sdk.utils;

import io.sphere.sdk.models.Base;
import org.javamoney.moneta.Money;

import javax.money.*;

final public class MoneyImpl extends Base implements MonetaryAmount {
    private final MonetaryAmount money;

    private MoneyImpl(final MonetaryAmount money) {
        this.money = money;
    }

    @Override
    public MonetaryContext getMonetaryContext() {
        return money.getMonetaryContext();
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
        boolean isEqual = false;
        if (obj != null && obj instanceof MonetaryAmount) {
            final MonetaryAmount other = (MonetaryAmount) obj;
            isEqual = asMoney().isEqualTo(Money.of(other.getNumber(), other.getCurrency()));
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return asMoney().hashCode();
    }

    public static MonetaryAmount of(final MonetaryAmount money) {
        return new MoneyImpl(money);
    }

    private Money asMoney() {
        return Money.of(getNumber(), getCurrency());
    }
}
