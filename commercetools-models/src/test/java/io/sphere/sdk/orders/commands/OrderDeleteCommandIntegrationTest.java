package io.sphere.sdk.orders.commands;

import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.queries.OrderByIdGet;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.orders.OrderFixtures.withOrder;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderDeleteCommandIntegrationTest extends IntegrationTest {
    @Test
    public void deleteById() throws Exception {
        withOrder(client(), order -> {
            final Order deletedOrder = client().executeBlocking(OrderDeleteCommand.of(order));
            final Order queriedDeletedOrder = client().executeBlocking(OrderByIdGet.of(deletedOrder));
            assertThat(queriedDeletedOrder).isNull();
        });
    }

    @Test
    public void deleteByOrderNumber() throws Exception {
        withOrder(client(), order -> {
            final Order deletedOrder = client().executeBlocking(OrderDeleteCommand.ofOrderNumber(order.getOrderNumber(), order.getVersion()));
            final Order queriedDeletedOrder = client().executeBlocking(OrderByIdGet.of(deletedOrder));
            assertThat(queriedDeletedOrder).isNull();
        });
    }
}