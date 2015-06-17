package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethod;
import static org.assertj.core.api.Assertions.assertThat;

public class ShippingMethodByIdFetchTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withShippingMethod(client(), shippingMethod -> {
            final ShippingMethodByIdFetch fetch = ShippingMethodByIdFetch.of(shippingMethod)
                    .withExpansionPaths(m -> m.taxCategory());
            final ShippingMethod loadedShippingMethod = execute(fetch).get();
            final String actualFetchedId = loadedShippingMethod.getId();
            assertThat(actualFetchedId).isEqualTo(shippingMethod.getId());
            assertThat(loadedShippingMethod.getTaxCategory().getObj()).isPresent();
        });
    }
}