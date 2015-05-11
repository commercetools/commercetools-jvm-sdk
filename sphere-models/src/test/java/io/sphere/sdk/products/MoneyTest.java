package io.sphere.sdk.products;

import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.json.JsonUtils;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.utils.SetUtils.asSet;
import static org.assertj.core.api.Assertions.assertThat;

public class MoneyTest {

    public static final MonetaryAmount MONEY = MoneyImpl.of(new BigDecimal("12345.67"), EUR);
    public static final String JSON = "{\"centAmount\":1234567,\"currencyCode\":\"EUR\"}";

    @Test
    public void serializeToJson() throws Exception {
        checkSerialization(MONEY, JSON);
    }

    @Test
    public void serializeWithMorePrecision() throws Exception {
        final String json = "{\"centAmount\":1234568,\"currencyCode\":\"EUR\"}";//rounds up
        checkSerialization(MoneyImpl.of(new BigDecimal("12345.6789"), EUR), json);
    }

    @Test
    public void serializeWithLessPrecision() throws Exception {
        checkSerialization(MoneyImpl.of(new BigDecimal("12345.6"), EUR), "{\"centAmount\":1234560,\"currencyCode\":\"EUR\"}");

    }

    @Test
    public void serializeWholeEuros() throws Exception {
        checkSerialization(MoneyImpl.of(new BigDecimal("12345"), EUR), "{\"centAmount\":1234500,\"currencyCode\":\"EUR\"}");
    }

    @Test
    public void deserializeFromJson() throws Exception {
        final MonetaryAmount money = JsonUtils.readObjectFromJsonString(TypeReferences.monetaryAmountTypeReference(), JSON);
        assertThat(money.isEqualTo(MONEY)).isTrue();
    }

    @Test
    public void comparison() throws Exception {
        assertThat(MoneyImpl.of(355, EUR).hashCode()).isEqualTo(MoneyImpl.of(new BigDecimal("355"), EUR).hashCode());
        assertThat(asSet(MoneyImpl.of(355, EUR), MoneyImpl.of(98774, EUR)))
                .isEqualTo(asSet(MoneyImpl.of(355, EUR), MoneyImpl.of(98774, EUR)));
    }

    private void checkSerialization(final MonetaryAmount money, final String json) {
        final String jsonString = JsonUtils.toJson(money);
        assertThat(jsonString).isEqualTo(json);
    }
}
