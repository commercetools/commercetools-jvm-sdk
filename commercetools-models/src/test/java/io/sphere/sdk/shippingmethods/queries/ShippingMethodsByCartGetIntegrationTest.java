package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.shippingmethods.ZoneRate;
import io.sphere.sdk.test.IntegrationTest;
import org.assertj.core.api.Condition;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.sphere.sdk.carts.CartFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;


public class ShippingMethodsByCartGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final Cart cart = createCartWithShippingAddress(client());

        final List<ShippingMethod> shippingMethods =
                client().executeBlocking(ShippingMethodsByCartGet.of(cart));
        assertThat(shippingMethods).isNotEmpty();

        for (final ShippingMethod shippingMethod : shippingMethods) {
            final List<ShippingRate> shippingRates = shippingMethod.getZoneRates().stream()
                    .flatMap(zoneRate -> zoneRate.getShippingRates().stream())
                    .collect(Collectors.toList());

            assertThat(shippingRates).areExactly(1, new Condition<>(ShippingRate::isMatching, "Shipping rate is matching"));
        }
    }
}