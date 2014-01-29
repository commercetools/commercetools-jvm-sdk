package io.sphere.client.shop.model;

import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import java.util.Set;

/** An order is the final state of a cart, usually created after a checkout process has been completed. */
public class Order extends LineItemContainer {
    private DateTime completedAt;
    @Nonnull private OrderState orderState;
    private ShipmentState shipmentState;
    private PaymentState paymentState;
    private Set<SyncInfo> syncInfo;

    // for JSON deserializer
    protected Order() {}

    /** Date and time when this order was completed. Optional. */
    public DateTime getCompletedAt() { return completedAt; }

    /** State in which this order is. */
    @Nonnull public OrderState getOrderState() { return orderState; }

    /** State of shipping for this order. Optional. */
    public ShipmentState getShipmentState() { return shipmentState; }

    /** State of the payment for this order. Optional. */
    public PaymentState getPaymentState() { return paymentState; }

    public Set<SyncInfo> getSyncInfo() {
        return syncInfo;
    }

    @Override
    public String toString() {
        return "Order{" +
                "completedAt=" + completedAt +
                ", orderState=" + orderState +
                ", shipmentState=" + shipmentState +
                ", paymentState=" + paymentState +
                '}';
    }
}
