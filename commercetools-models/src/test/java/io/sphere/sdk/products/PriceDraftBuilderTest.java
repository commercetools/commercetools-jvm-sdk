package io.sphere.sdk.products;

import io.sphere.sdk.models.DefaultCurrencyUnits;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link PriceDraftBuilder}.
 */
public class PriceDraftBuilderTest {

    @Test
    public void copyPriceWithoutCustom() {
        final Price price = Price.of(BigDecimal.TEN, DefaultCurrencyUnits.EUR);
        final PriceDraftBuilder priceDraftBuilder = PriceDraftBuilder.of(price);

        assertThat(priceDraftBuilder).isNotNull();
    }
}
