package io.sphere.sdk.shippingmethods.queries;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.orderedit.OrderEditFixtures;
import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.orders.OrderFixtures;
import io.sphere.sdk.orders.queries.OrderByIdGet;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.utils.VrapRequestDecorator;
import org.assertj.core.api.Condition;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethodForGermany;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class ShippingMethodsByOrderEditGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withShippingMethodForGermany(client(), shippingMethod -> {
            OrderFixtures.withOrder(client(), order -> {
                OrderEditFixtures.withOrderEdit(client(), order.toReference(), orderEdit -> {
                    final SphereRequest<List<ShippingMethod>> sphereRequest =
                            new VrapRequestDecorator<>(ShippingMethodsByOrderEditGet.of(orderEdit, CountryCode.DE), "response");

                    final List<ShippingMethod> shippingMethodsByOrderEdit =
                            client().executeBlocking(sphereRequest);
                    assertThat(shippingMethodsByOrderEdit).isNotEmpty();

                    for (final ShippingMethod shippingMethodByOrderEdit : shippingMethodsByOrderEdit) {
                        final List<ShippingRate> shippingRates = shippingMethodByOrderEdit.getZoneRates().stream()
                                .flatMap(zoneRate -> zoneRate.getShippingRates().stream())
                                .collect(Collectors.toList());

                        assertThat(shippingRates).areAtLeastOne(new Condition<>(ShippingRate::isMatching, "Shipping rate is matching"));
                        assertThat(shippingMethodByOrderEdit.getZones()).isNotEmpty();
                    }
                });
            });
        });
    }
}