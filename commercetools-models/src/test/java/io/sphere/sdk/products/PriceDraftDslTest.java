package io.sphere.sdk.products;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class PriceDraftDslTest {
    @Test
    public void withCountryCode() {
        final PriceDraft draft = PriceDraftDsl.of(MoneyImpl.ofCents(123, "EUR")).withCountryCode("DE");
        assertThat(draft.getCountry()).isEqualTo(CountryCode.DE);
    }

    @Test
    public void withCustomerGroupId() {
        final String customerGroupId = "foo";
        final PriceDraft draft = PriceDraftDsl.of(MoneyImpl.ofCents(123, "EUR")).withCustomerGroupId(customerGroupId);
        assertThat(draft.getCustomerGroup().getId()).isEqualTo(customerGroupId);
    }
}