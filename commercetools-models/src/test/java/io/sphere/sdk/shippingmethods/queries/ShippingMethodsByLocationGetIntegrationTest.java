package io.sphere.sdk.shippingmethods.queries;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.test.IntegrationTest;
import org.assertj.core.api.Condition;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.sphere.sdk.models.DefaultCurrencyUnits.USD;
import static org.assertj.core.api.Assertions.assertThat;

public class ShippingMethodsByLocationGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final List<ShippingMethod> shippingMethods =
                client().executeBlocking(ShippingMethodsByLocationGet.of(CountryCode.US, "Kansas", USD));
        assertThat(shippingMethods).isNotEmpty();

        for (final ShippingMethod shippingMethod : shippingMethods) {
            final List<ShippingRate> shippingRates = shippingMethod.getZoneRates().stream()
                    .flatMap(zoneRate -> zoneRate.getShippingRates().stream())
                    .collect(Collectors.toList());

            assertThat(shippingRates).areAtLeastOne(new Condition<>(ShippingRate::isMatching, "Shipping rate is matching"));
        }
    }
}