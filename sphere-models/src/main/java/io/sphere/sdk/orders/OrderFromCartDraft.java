package io.sphere.sdk.orders;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Versioned;

import java.util.Optional;

public class OrderFromCartDraft extends Base {
    private final String id;
    private final long version;
    private final Optional<String> orderNumber;
    private final Optional<PaymentState>  paymentState;

    private OrderFromCartDraft(final String id, final long version, final Optional<String> orderNumber, final Optional<PaymentState> paymentState) {
        this.id = id;
        this.version = version;
        this.orderNumber = orderNumber;
        this.paymentState = paymentState;
    }

    public static OrderFromCartDraft of(final Versioned<Cart> cart, final Optional<String> orderNumber, final Optional<PaymentState> paymentState) {
        return new OrderFromCartDraft(cart.getId(), cart.getVersion(), orderNumber, paymentState);
    }

    public static OrderFromCartDraft of(final Versioned<Cart> cart) {
        return new OrderFromCartDraft(cart.getId(), cart.getVersion(), Optional.empty(), Optional.empty());
    }

    public String getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public Optional<String> getOrderNumber() {
        return orderNumber;
    }

    public Optional<PaymentState> getPaymentState() {
        return paymentState;
    }
}
