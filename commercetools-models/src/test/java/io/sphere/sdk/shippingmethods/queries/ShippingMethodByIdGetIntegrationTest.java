package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethod;
import static org.assertj.core.api.Assertions.assertThat;

public class ShippingMethodByIdGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withShippingMethod(client(), shippingMethod -> {
            final ShippingMethodByIdGet fetch = ShippingMethodByIdGet.of(shippingMethod)
                    .withExpansionPaths(m -> m.taxCategory());
            final ShippingMethod loadedShippingMethod = client().executeBlocking(fetch);
            final String actualFetchedId = loadedShippingMethod.getId();
            assertThat(actualFetchedId).isEqualTo(shippingMethod.getId());
            assertThat(loadedShippingMethod.getTaxCategory().getObj()).isNotNull();
        });
    }
}