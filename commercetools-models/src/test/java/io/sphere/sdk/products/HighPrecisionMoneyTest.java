package io.sphere.sdk.products;

import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.utils.HighPrecisionMoneyImpl;
import io.sphere.sdk.utils.MoneyImpl;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryQueries;
import org.junit.Test;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.utils.SphereInternalUtils.asSet;
import static org.assertj.core.api.Assertions.assertThat;

public class HighPrecisionMoneyTest {


    public static final String EUR_HIGH_PRECISION = "{\"type\":\"highPrecision\",\"currencyCode\":\"EUR\",\"centAmount\":1234568,\"preciseAmount\":1234567891234567891234567,\"fractionDigits\":20}";
    public static final MonetaryAmount EUR_HIGH_PRECISION_AMOUNT = MoneyImpl.of(new BigDecimal("12345.67891234567891234567"), EUR);

    @Test
    public void serializeWithMorePrecision() throws Exception {
        checkSerialization(HighPrecisionMoneyImpl.of(MoneyImpl.of(new BigDecimal("12345.67891234567891234567"), EUR),20), EUR_HIGH_PRECISION);
    }

    @Test
    public void deserializeFromJsonInJen() throws Exception {
        final MonetaryAmount money = SphereJsonUtils.readObject(EUR_HIGH_PRECISION, TypeReferences.monetaryAmountTypeReference());
        assertThat(money.isEqualTo(EUR_HIGH_PRECISION_AMOUNT)).isTrue();
    }

    private void checkSerialization(final MonetaryAmount money, final String json) {
        final String jsonString = SphereJsonUtils.toJsonString(money);
        assertThat(jsonString).isEqualTo(json);
    }
}
