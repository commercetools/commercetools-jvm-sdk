package io.sphere.internal.command;

import com.google.common.base.Optional;
import io.sphere.client.shop.model.*;

import net.jcip.annotations.Immutable;

import java.util.List;

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
    public static final class AddDelivery extends UpdateAction {
        private final List<DeliveryItem> items;

        public AddDelivery(List<DeliveryItem> items) {
            super("addDelivery");
            this.items = items;
        }

        public List<DeliveryItem> getItems() {
            return items;
        }
    }

    @Immutable
    public static final class AddParcelToDelivery extends UpdateAction {
        private final String deliveryId;
        private final ParcelMeasurements measurements;
        private final TrackingData trackingData;


        public AddParcelToDelivery(String deliveryId, Optional<ParcelMeasurements> measurements, Optional<TrackingData> trackingData) {
            super("addParcelToDelivery");
            this.deliveryId = deliveryId;
            this.measurements = measurements.orNull();
            this.trackingData = trackingData.orNull();
        }

        public String getDeliveryId() {
            return deliveryId;
        }

        public ParcelMeasurements getMeasurements() {
            return measurements;
        }

        public TrackingData getTrackingData() {
            return trackingData;
        }

        @Override
        public String toString() {
            return "AddParcelToDelivery{" +
                    "deliveryId='" + deliveryId + '\'' +
                    ", measurements=" + measurements +
                    ", trackingData=" + trackingData +
                    '}';
        }
    }
}
