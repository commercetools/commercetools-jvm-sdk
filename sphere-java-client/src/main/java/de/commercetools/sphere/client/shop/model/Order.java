package de.commercetools.sphere.client.shop.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.joda.time.DateTime;

/** Order that is stored persistently in the backend. It can be created from a cart.
 *  Once the order is created on the backend, it can be fetched by id
 *  and an instance of a PersistentCart is returned.
 *
 *  All the read methods of this class read from memory and all the
 *  methods making changes to this instance also persist the changes on the backend. */
@JsonIgnoreProperties(ignoreUnknown=true) // temp until this class fully matches the json returned by the backend
public class Order extends LineItemContainer {
    private DateTime completedAt;
    private OrderState orderState;
    private ShipmentState shipmentState;
    private PaymentState paymentState;

    // for JSON deserializer
    private Order() {}

    public DateTime getCompletedAt() {
        return completedAt;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public ShipmentState getShipmentState() {
        return shipmentState;
    }

    public PaymentState getPaymentState() {
        return paymentState;
    }

    /**
     * Describes the payment state of the order.
     */
    public enum PaymentState {
        BalanceDue, Failed, Pending, CreditOwed, Paid
    }

    /**
     * Describes the shipment state of the order.
     */
    public enum ShipmentState {
        Shipped, Ready, Pending, Partial, Backorder
    }

    /**
     * Describes the state of the order.
     */
    public enum OrderState {
        Open, Complete
    }
}
