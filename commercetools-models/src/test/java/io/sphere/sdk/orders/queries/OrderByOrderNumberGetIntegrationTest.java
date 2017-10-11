package io.sphere.sdk.orders.queries;

import io.sphere.sdk.orders.Order;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.orders.OrderFixtures.withOrder;
import static org.assertj.core.api.Assertions.assertThat;


public class OrderByOrderNumberGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withOrder(client(), order -> {
            final Order loadedOrder = client().executeBlocking(OrderByOrderNumberGet.of(order.getOrderNumber()));
            assertThat(loadedOrder.getOrderNumber()).isEqualTo(order.getOrderNumber());
            return order;
        });
    }
}