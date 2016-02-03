package io.sphere.sdk.models;

import com.neovisionaries.i18n.CountryCode;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public final class AddressTest {
    @Test
    public void equalsIgnoreId() {
        final Address addressWithoutId = Address.of(CountryCode.DE);
        final Address addressWithId = addressWithoutId.withId("foo");
        assertThat(addressWithoutId)
                .isNotEqualTo(addressWithId)
                .matches(address -> address.equalsIgnoreId(addressWithId));
    }
}