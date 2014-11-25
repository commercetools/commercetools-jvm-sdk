package io.sphere.sdk.orders.commands;

import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderState;
import io.sphere.sdk.orders.commands.updateactions.ChangeOrderState;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.orders.OrderFixtures.withOrder;
import static org.fest.assertions.Assertions.assertThat;

public class OrderUpdateCommandTest extends IntegrationTest {
    @Test
    public void changeOrderState() throws Exception {
        withOrder(client(), order -> {
            assertThat(order.getOrderState()).isEqualTo(OrderState.Open);
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, ChangeOrderState.of(OrderState.Complete)));
            assertThat(updatedOrder.getOrderState()).isEqualTo(OrderState.Complete);
        });
    }
}