package io.sphere.sdk.orders.queries;

import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.commands.OrderUpdateCommand;
import io.sphere.sdk.orders.commands.updateactions.SetOrderNumber;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.orders.OrderFixtures.withOrder;
import static io.sphere.sdk.test.SphereTestUtils.randomString;
import static org.assertj.core.api.Assertions.assertThat;


public class OrderByOrderNumberGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withOrder(client(), order -> {
            final String orderNumber = randomString();
            final Order orderWithOrderNumber = client().executeBlocking(OrderUpdateCommand.of(order,
                    SetOrderNumber.of(orderNumber)));
            final Order loadedOrder = client().executeBlocking(OrderByOrderNumberGet.of(orderNumber));

            assertThat(loadedOrder.getOrderNumber()).isEqualTo(orderNumber);
            return orderWithOrderNumber;
        });
    }
}