package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.ShipmentState;

/**

 {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandTest#changeShipmentState()}
 */
public class ChangeShipmentState extends UpdateAction<Order> {
    private final ShipmentState shipmentState;

    private ChangeShipmentState(final ShipmentState shipmentState) {
        super("changeShipmentState");
        this.shipmentState = shipmentState;
    }

    public static ChangeShipmentState of(final ShipmentState shipmentState) {
        return new ChangeShipmentState(shipmentState);
    }

    public ShipmentState getShipmentState() {
        return shipmentState;
    }
}
