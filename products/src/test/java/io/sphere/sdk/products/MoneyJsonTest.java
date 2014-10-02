package io.sphere.sdk.products;

import io.sphere.sdk.models.TypeReferences;
import io.sphere.sdk.utils.JsonUtils;
import org.javamoney.moneta.Money;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

import static org.fest.assertions.Assertions.assertThat;

public class MoneyJsonTest {

    public static final MonetaryAmount MONEY = Money.of(new BigDecimal("12345.67"), "EUR");
    public static final String JSON = "{\"centAmount\":1234567,\"currencyCode\":\"EUR\"}";

    @Test
    public void serializeToJson() throws Exception {
        checkSerialization(MONEY, JSON);
    }

    @Test
    public void serializeWithMorePrecision() throws Exception {
        final String json = "{\"centAmount\":1234568,\"currencyCode\":\"EUR\"}";//rounds up
        checkSerialization(Money.of(new BigDecimal("12345.6789"), "EUR"), json);
    }

    @Test
    public void serializeWithLessPrecision() throws Exception {
        checkSerialization(Money.of(new BigDecimal("12345.6"), "EUR"), "{\"centAmount\":1234560,\"currencyCode\":\"EUR\"}");

    }

    @Test
    public void serializeWholeEuros() throws Exception {
        checkSerialization(Money.of(new BigDecimal("12345"), "EUR"), "{\"centAmount\":1234500,\"currencyCode\":\"EUR\"}");
    }

    @Test
    public void deserializeFromJson() throws Exception {
        final MonetaryAmount money = JsonUtils.readObjectFromJsonString(TypeReferences.monetaryAmountTypeReference(), JSON);
        assertThat(money).isEqualTo(MONEY);
    }

    private void checkSerialization(final MonetaryAmount money, final String json) {
        final String jsonString = JsonUtils.toJson(money);
        assertThat(jsonString).isEqualTo(json);
    }
}
