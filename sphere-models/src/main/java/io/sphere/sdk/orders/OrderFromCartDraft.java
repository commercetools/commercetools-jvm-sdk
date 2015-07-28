package io.sphere.sdk.orders;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Versioned;

import javax.annotation.Nullable;

public class OrderFromCartDraft extends Base {
    private final String id;
    private final Long version;
    @Nullable
    private final String orderNumber;
    @Nullable
    private final PaymentState  paymentState;

    private OrderFromCartDraft(final String id, final Long version, @Nullable final String orderNumber, @Nullable final PaymentState paymentState) {
        this.id = id;
        this.version = version;
        this.orderNumber = orderNumber;
        this.paymentState = paymentState;
    }

    public static OrderFromCartDraft of(final Versioned<Cart> cart, @Nullable final String orderNumber, @Nullable final PaymentState paymentState) {
        return new OrderFromCartDraft(cart.getId(), cart.getVersion(), orderNumber, paymentState);
    }

    public static OrderFromCartDraft of(final Versioned<Cart> cart) {
        return new OrderFromCartDraft(cart.getId(), cart.getVersion(), null, null);
    }

    public String getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    @Nullable
    public String getOrderNumber() {
        return orderNumber;
    }

    @Nullable
    public PaymentState getPaymentState() {
        return paymentState;
    }
}
