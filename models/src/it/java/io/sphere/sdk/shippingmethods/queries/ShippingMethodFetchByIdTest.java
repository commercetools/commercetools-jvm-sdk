package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethod;
import static org.fest.assertions.Assertions.assertThat;

public class ShippingMethodFetchByIdTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withShippingMethod(client(), shippingMethod -> {
            final ShippingMethodFetchById fetch = ShippingMethodFetchById.of(shippingMethod.getId());
            final String actualFetchedId = execute(fetch).get().getId();
            assertThat(actualFetchedId).isEqualTo(shippingMethod.getId());
        });
    }
}