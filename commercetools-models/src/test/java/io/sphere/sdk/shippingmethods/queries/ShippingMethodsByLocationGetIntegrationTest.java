package io.sphere.sdk.shippingmethods.queries;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.utils.VrapRequestDecorator;
import org.assertj.core.api.Condition;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethodForGermany;
import static org.assertj.core.api.Assertions.assertThat;

public class ShippingMethodsByLocationGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withShippingMethodForGermany(client(), shippingMethod -> {
            final SphereRequest<List<ShippingMethod>> sphereRequest =
                    new VrapRequestDecorator<>(ShippingMethodsByLocationGet.of(CountryCode.DE).withExpansionPaths(m -> m.zones()), "response");

            final List<ShippingMethod> shippingMethodsByLocation =
                    client().executeBlocking(sphereRequest);
            assertThat(shippingMethodsByLocation).isNotEmpty();

            for (final ShippingMethod shippingMethodByLocation : shippingMethodsByLocation) {
                final List<ShippingRate> shippingRates = shippingMethodByLocation.getZoneRates().stream()
                        .flatMap(zoneRate -> zoneRate.getShippingRates().stream())
                        .collect(Collectors.toList());

                assertThat(shippingRates).areAtLeastOne(new Condition<>(ShippingRate::isMatching, "Shipping rate is matching"));
                assertThat(shippingMethodByLocation.getZones()).isNotEmpty();
                assertThat(shippingMethodByLocation.getZones().get(0).getObj()).isNotNull();
            }
        });
    }
}