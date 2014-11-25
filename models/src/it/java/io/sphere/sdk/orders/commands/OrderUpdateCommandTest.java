package io.sphere.sdk.orders.commands;

import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderState;
import io.sphere.sdk.orders.PaymentState;
import io.sphere.sdk.orders.ShipmentState;
import io.sphere.sdk.orders.commands.updateactions.*;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.orders.OrderFixtures.withOrder;
import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;

public class OrderUpdateCommandTest extends IntegrationTest {
    @Test
    public void changeOrderState() throws Exception {
        withOrder(client(), order -> {
            assertThat(order.getOrderState()).isEqualTo(OrderState.Open);
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, ChangeOrderState.of(OrderState.Complete)));
            assertThat(updatedOrder.getOrderState()).isEqualTo(OrderState.Complete);
        });
    }

    @Test
    public void changeShipmentState() throws Exception {
        withOrder(client(), order -> {
            assertThat(order.getShipmentState()).isEqualTo(Optional.empty());
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, ChangeShipmentState.of(ShipmentState.Shipped)));
            assertThat(updatedOrder.getShipmentState()).isPresentAs(ShipmentState.Shipped);
        });
    }

    @Test
    public void changePaymentState() throws Exception {
        withOrder(client(), order -> {
            assertThat(order.getPaymentState()).isEqualTo(Optional.empty());
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, ChangePaymentState.of(PaymentState.Paid)));
            assertThat(updatedOrder.getPaymentState()).isPresentAs(PaymentState.Paid);
        });
    }
}