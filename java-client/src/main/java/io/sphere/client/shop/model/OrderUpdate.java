package io.sphere.client.shop.model;

import io.sphere.internal.command.OrderCommands;
import io.sphere.internal.command.Update;
import io.sphere.internal.command.UpdateAction;

public class OrderUpdate extends Update<UpdateAction> {
    public OrderUpdate setPaymentState(PaymentState paymentState) {
        add(new OrderCommands.UpdatePaymentState(paymentState));
        return this;
    }

    public OrderUpdate setShipmentState(ShipmentState shipmentState) {
        add(new OrderCommands.UpdateShipmentState(shipmentState));
        return this;
    }
}
