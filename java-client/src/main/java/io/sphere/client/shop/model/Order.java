package io.sphere.client.shop.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import java.util.Set;

/** An order is the final state of a cart, usually created after a checkout process has been completed. */
public class Order extends LineItemContainer {
    private DateTime completedAt;
    @JsonProperty("orderNumber") private String orderNumber = "";
    @Nonnull private OrderState orderState;
    private ShipmentState shipmentState;
    private PaymentState paymentState;
    private Set<SyncInfo> syncInfo;

    // for JSON deserializer
    protected Order() {}

    /** String that uniquely identifies an order. Optional.
     * It is used to create more human-readable (in contrast to ID) identifier for the order.
     * It is unique within a project. */
    public String getOrderNumber() { return orderNumber; }

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
                ", orderNumber=" + orderNumber +
                ", orderState=" + orderState +
                ", shipmentState=" + shipmentState +
                ", paymentState=" + paymentState +
                '}';
    }
}
