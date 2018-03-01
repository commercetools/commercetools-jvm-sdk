package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingMethodDraftBuilder;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethod;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withUpdateableShippingMethod;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static io.sphere.sdk.test.SphereTestUtils.randomString;
import static org.assertj.core.api.Assertions.assertThat;

public class ShippingMethodByKeyGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final String key = randomKey();
        withUpdateableShippingMethod(client(), builder -> builder.key(key), shippingMethod -> {
            final ShippingMethodByKeyGet getByKey = ShippingMethodByKeyGet.of(key);
            final ShippingMethod shippingMethodByKey = client().executeBlocking(getByKey);

            assertThat(shippingMethodByKey).isNotNull();
            assertThat(shippingMethodByKey.getKey()).isEqualTo(key);

            return shippingMethod;
        });
    }
}