package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.queries.Sort;
import io.sphere.sdk.queries.SortDirection;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingMethodFixtures;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.queries.SortDirection.DESC;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethod;
import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;

public class ShippingMethodQueryModelTest extends IntegrationTest {

    public static final Sort<ShippingMethod> BY_CREATED_AT_DESC = ShippingMethodQuery.model().createdAt().sort(DESC);

    @Test
    public void queryByName() throws Exception {
        withShippingMethod(client(), shippingMethod -> {
            final ShippingMethod actual = execute(ShippingMethodQuery.of()
                    .byName(shippingMethod.getName())
                    .withSort(BY_CREATED_AT_DESC))
                    .head().get();
            assertThat(actual.getId()).isEqualTo(shippingMethod.getId());
        });
    }

    @Test
    public void queryByTaxCategory() throws Exception {
        withShippingMethod(client(), shippingMethod -> {
            final ShippingMethod actual = execute(ShippingMethodQuery.of()
                    .byTaxCategory(shippingMethod.getTaxCategory())
                    .withSort(BY_CREATED_AT_DESC))
                    .head().get();
            assertThat(actual.getId()).isEqualTo(shippingMethod.getId());
        });
    }
}