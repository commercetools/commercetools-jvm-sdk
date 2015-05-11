package io.sphere.sdk.orders.queries;

import io.sphere.sdk.orders.Order;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.orders.OrderFixtures.withOrder;
import static org.assertj.core.api.Assertions.assertThat;


public class OrderByIdFetchTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withOrder(client(), order -> {
            final Order loadedOrder = execute(OrderByIdFetch.of(order.getId())).get();
            assertThat(loadedOrder.getId()).isEqualTo(order.getId());
        });
    }
}