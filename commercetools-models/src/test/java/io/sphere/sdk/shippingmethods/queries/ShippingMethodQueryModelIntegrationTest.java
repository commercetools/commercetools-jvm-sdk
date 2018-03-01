package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.commands.ShippingMethodUpdateCommand;
import io.sphere.sdk.shippingmethods.commands.updateactions.SetKey;
import io.sphere.sdk.test.IntegrationTest;
import net.jcip.annotations.NotThreadSafe;
import org.junit.Test;

import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethod;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withUpdateableShippingMethod;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

@NotThreadSafe
public class ShippingMethodQueryModelIntegrationTest extends IntegrationTest {

    public static final QuerySort<ShippingMethod> BY_CREATED_AT_DESC = ShippingMethodQueryModel.of().createdAt().sort().desc();

    @Test
    public void queryByName() throws Exception {
        withShippingMethod(client(), shippingMethod -> {
            final ShippingMethod actual = client().executeBlocking(ShippingMethodQuery.of()
                    .byName(shippingMethod.getName())
                    .withSort(BY_CREATED_AT_DESC))
                    .head().get();
            assertThat(actual.getId()).isEqualTo(shippingMethod.getId());
        });
    }

    @Test
    public void queryByKey() throws Exception {
        withUpdateableShippingMethod(client(), shippingMethod -> {
            final String key = randomKey();
            final ShippingMethod updatedShippingMethod = client().executeBlocking(ShippingMethodUpdateCommand.of(shippingMethod, SetKey.of(key)));
            final ShippingMethod actual = client().executeBlocking(ShippingMethodQuery.of()
                    .byKey(key)
                    .withSort(BY_CREATED_AT_DESC))
                    .head().get();
            assertThat(actual.getId()).isEqualTo(updatedShippingMethod.getId());
        });
    }

    @Test
    public void queryByTaxCategory() throws Exception {
        withShippingMethod(client(), shippingMethod -> {
            final ShippingMethod actual = client().executeBlocking(ShippingMethodQuery.of()
                    .byTaxCategory(shippingMethod.getTaxCategory())
                    .withSort(BY_CREATED_AT_DESC))
                    .head().get();
            assertThat(actual.getId()).isEqualTo(shippingMethod.getId());
        });
    }
}