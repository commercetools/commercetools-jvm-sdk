package de.commercetools.internal;

import java.util.Currency;

import de.commercetools.sphere.client.shop.model.*;

import net.jcip.annotations.Immutable;


/** Commands issued against the HTTP endpoints for working with shopping carts. */
public class OrderCommands {

//    @Immutable
//    public static final class CreateOrder extends CommandBase {
//        private final String customerId;
//
//        public CreateOrder(String customerId) {
//            super(id, version);
//            this.customerId = customerId;
//        }
//
//        public String getCustomerId() { return customerId; }
//    }

    @Immutable
    public static final class UpdateShipmentState extends CommandBase {
        private final Order.ShipmentState shipmentState;

        public UpdateShipmentState(String id, int version, Order.ShipmentState shipmentState) {
            super(id, version);
            this.shipmentState = shipmentState;
        }

        public Order.ShipmentState getShipmentState() { return shipmentState; }
    }

    @Immutable
    public static final class UpdatePaymentState extends CommandBase {
        private final Order.PaymentState paymentState;

        public UpdatePaymentState(String id, int version, Order.PaymentState paymentState) {
            super(id, version);
            this.paymentState = paymentState;
        }

        public Order.PaymentState getPaymentState() { return paymentState; }
    }

}
