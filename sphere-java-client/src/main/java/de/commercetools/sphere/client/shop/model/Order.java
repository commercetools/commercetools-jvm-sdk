package de.commercetools.sphere.client.shop.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.joda.time.DateTime;

/** Order that is stored persistently in the backend. It can be created from a cart.
 *  Once the order is created on the backend, it can be fetched by id
 *  and an instance of a PersistentCart is returned.
 *
 *  All the read methods of this class read from memory and all the
 *  methods making changes to this instance also persist the changes on the backend. */
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
}
