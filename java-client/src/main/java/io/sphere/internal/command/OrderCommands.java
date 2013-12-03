package io.sphere.internal.command;

import io.sphere.client.shop.model.*;

import net.jcip.annotations.Immutable;


/** Commands issued against the HTTP endpoints for working with shopping orders. */
public class OrderCommands {

    @Immutable
    public static final class UpdateShipmentState extends UpdateAction {
        private final ShipmentState shipmentState;

        public UpdateShipmentState(ShipmentState shipmentState) {
            super("changeShipmentState");
            this.shipmentState = shipmentState;
        }

        public ShipmentState getShipmentState() { return shipmentState; }
    }

    @Immutable
    public static final class UpdatePaymentState extends UpdateAction {
        private final PaymentState paymentState;

        public UpdatePaymentState(PaymentState paymentState) {
            super("changePaymentState");
            this.paymentState = paymentState;
        }

        public PaymentState getPaymentState() { return paymentState; }
    }

}
