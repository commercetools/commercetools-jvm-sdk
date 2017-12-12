package io.sphere.sdk.utils;

import org.junit.Test;

import javax.money.MonetaryAmount;

import java.math.BigDecimal;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static org.assertj.core.api.Assertions.*;

public class MoneyImplTest {
    @Test
    public void ofCentsStringAndCurrencyCode() {
        final MonetaryAmount amount = MoneyImpl.ofCentsAndCurrencyCode("123456", "EUR");
        assertThat(amount).isEqualTo(MoneyImpl.of(new BigDecimal("1234.56"), EUR));
    }

    @Test
    public void toStringShowsUsableInput() {
        assertThat(MoneyImpl.ofCents(123456, "EUR").toString()).isEqualTo("EUR 1234.56");
        assertThat(MoneyImpl.of(MoneyImpl.ofCents(123456, "EUR")).toString()).isEqualTo("EUR 1234.56");
    }
}