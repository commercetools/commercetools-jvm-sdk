package io.sphere.sdk.utils;

import io.sphere.sdk.models.DefaultCurrencyUnits;
import org.junit.Test;

import javax.money.MonetaryAmount;

import java.math.BigDecimal;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static org.assertj.core.api.Assertions.*;

public class MoneyImplTest {
    @Test
    public void ofCentsStringAndCurrencyCode() {
        final MonetaryAmount amount = MoneyImpl.ofCentsAndCurrencyUnit("123456", "EUR");
        assertThat(amount).isEqualTo(MoneyImpl.of(new BigDecimal("1234.56"), EUR));
    }
}