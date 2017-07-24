package io.sphere.sdk.shippingmethods.commands;

import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.commands.updateactions.SetKey;
import io.sphere.sdk.shippingmethods.queries.ShippingMethodQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withUpdateableShippingMethod;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class ShippingMethodDeleteCommandIntegrationTest extends IntegrationTest {

    @Test
    public void byVersioned() {
        withUpdateableShippingMethod(client(), shippingMethod -> {
            assertThat(shippingMethod).isNotNull();
            final String shippingMethodId = shippingMethod.getId();
            client().executeBlocking(ShippingMethodDeleteCommand.of(shippingMethod));
            final ShippingMethodQuery shippingMethodQuery = ShippingMethodQuery.of().withPredicates(m -> m.id().is(shippingMethodId));
            assertThat(client().executeBlocking(shippingMethodQuery).getCount()).isZero();
        });
    }

    @Test
    public void byKey() {
        withUpdateableShippingMethod(client(), shippingMethod -> {
            final String key = randomKey();
            final ShippingMethodUpdateCommand cmd = ShippingMethodUpdateCommand.of(shippingMethod, SetKey.of(key));
            final ShippingMethod updatedShippingMethod = client().executeBlocking(cmd);
            assertThat(updatedShippingMethod.getKey()).isEqualTo(key);

            client().executeBlocking(ShippingMethodDeleteCommand.ofKey(key, updatedShippingMethod.getVersion()));
            final ShippingMethodQuery shippingMethodQuery = ShippingMethodQuery.of().withPredicates(m -> m.key().is(key));
            assertThat(client().executeBlocking(shippingMethodQuery).getCount()).isZero();
        });
    }
}