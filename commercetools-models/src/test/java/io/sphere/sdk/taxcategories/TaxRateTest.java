package io.sphere.sdk.taxcategories;

import com.neovisionaries.i18n.CountryCode;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class TaxRateTest {
    @Test
    public void equalsIgnoreId() {
        final TaxRateBuilder builder = TaxRateBuilder.of("name", 0.2, true, CountryCode.DE);
        final TaxRate taxRateWithoutId = builder.build();
        final TaxRate taxRateWithId = builder.id("foo").build();
        assertThat(taxRateWithoutId)
                .isNotEqualTo(taxRateWithId)
                .matches(taxRate -> taxRate.equalsIgnoreId(taxRateWithId));
    }
}