package io.sphere.sdk.products;

import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.utils.MoneyImpl;
import org.javamoney.moneta.function.MonetaryQueries;
import org.junit.Test;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.utils.SphereInternalUtils.asSet;
import static org.assertj.core.api.Assertions.assertThat;

public class MoneyTest {
    public static final MonetaryAmount MONEY = MoneyImpl.of(new BigDecimal("12345.67"), EUR);
    public static final MonetaryAmount MONEY_YEN = MoneyImpl.of(new BigDecimal("12345"), "JPY");
    public static final String JSON = "{\"type\":\"centPrecision\",\"centAmount\":1234567,\"currencyCode\":\"EUR\"}";
    public static final String JSON_YEN = "{\"type\":\"centPrecision\",\"centAmount\":12345,\"currencyCode\":\"JPY\"}";

    @Test
    public void serializeToJson() throws Exception {
        checkSerialization(MONEY, JSON);
    }

    @Test
    public void serializeToJsonInYen() throws Exception {
        checkSerialization(MONEY_YEN, JSON_YEN);
    }

    @Test
    public void serializeWithMorePrecision() throws Exception {
        final String json = "{\"type\":\"centPrecision\",\"centAmount\":1234568,\"currencyCode\":\"EUR\"}";//rounds up
        checkSerialization(MoneyImpl.of(new BigDecimal("12345.6789"), EUR), json);
    }

    @Test
    public void serializeWithLessPrecision() throws Exception {
        checkSerialization(MoneyImpl.of(new BigDecimal("12345.6"), EUR), "{\"type\":\"centPrecision\",\"centAmount\":1234560,\"currencyCode\":\"EUR\"}");

    }

    @Test
    public void serializeWholeEuros() throws Exception {
        checkSerialization(MoneyImpl.of(new BigDecimal("12345"), EUR), "{\"type\":\"centPrecision\",\"centAmount\":1234500,\"currencyCode\":\"EUR\"}");
    }

    @Test
    public void deserializeFromJson() throws Exception {
        final MonetaryAmount money = SphereJsonUtils.readObject(JSON, TypeReferences.monetaryAmountTypeReference());
        assertThat(money.isEqualTo(MONEY)).isTrue();
    }

    @Test
    public void deserializeFromJsonInJen() throws Exception {
        final MonetaryAmount money = SphereJsonUtils.readObject(JSON_YEN, TypeReferences.monetaryAmountTypeReference());
        assertThat(money.isEqualTo(MONEY_YEN)).isTrue();
    }

    @Test
    public void comparison() throws Exception {
        assertThat(MoneyImpl.of(355, EUR).hashCode()).isEqualTo(MoneyImpl.of(new BigDecimal("355"), EUR).hashCode());
        assertThat(asSet(MoneyImpl.of(355, EUR), MoneyImpl.of(98774, EUR)))
                .isEqualTo(asSet(MoneyImpl.of(355, EUR), MoneyImpl.of(98774, EUR)));
    }

    @Test
    public void ofCents() throws Exception {
        assertThat(MoneyImpl.of(new BigDecimal("123.45"), EUR)).isEqualTo(MoneyImpl.ofCents(12345, "EUR"));
    }

    @Test
    public void yen() throws Exception {
        assertThat(MoneyImpl.ofCents(123456, "JPY")).isEqualTo(MoneyImpl.of(123456, Monetary.getCurrency("JPY")));
        assertThat(MoneyImpl.of(123456, Monetary.getCurrency("JPY")).query(MonetaryQueries.extractMajorPart())).isEqualTo(123456);
        assertThat(MoneyImpl.of(123456, Monetary.getCurrency("JPY")).query(MonetaryQueries.extractMajorPart())).isEqualTo(123456);
        assertThat(MoneyImpl.of(new BigDecimal("1234.56"), Monetary.getCurrency("JPY")).query(MonetaryQueries.extractMajorPart())).isEqualTo(1234);
        assertThat(MoneyImpl.of(new BigDecimal("1234.56"), Monetary.getCurrency("JPY")).query(MonetaryQueries.extractMinorPart())).isEqualTo(0);
    }

    private void checkSerialization(final MonetaryAmount money, final String json) {
        final String jsonString = SphereJsonUtils.toJsonString(money);
        assertThat(jsonString).isEqualTo(json);
    }
}
