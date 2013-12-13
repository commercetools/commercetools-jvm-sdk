package io.sphere.internal.command;

import io.sphere.client.shop.model.*;

import net.jcip.annotations.Immutable;

import static com.google.common.base.Strings.emptyToNull;

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


    @Immutable
    public static final class AddTrackingData extends UpdateAction {
        private final String trackingId;
        private final String carrier;
        private final boolean isReturn;

        public AddTrackingData(final TrackingData trackingData) {
            super("addTrackingData");
            this.trackingId = trackingData.getTrackingId();
            this.carrier = emptyToNull(trackingData.getCarrier());
            this.isReturn = trackingData.isReturn();
        }

        public String getTrackingId() { return trackingId; }

        public String getCarrier() { return carrier; }

        public boolean isReturn() { return isReturn; }
    }
}
