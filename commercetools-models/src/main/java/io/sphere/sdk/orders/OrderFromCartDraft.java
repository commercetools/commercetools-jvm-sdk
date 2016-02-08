package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.models.Versioned;

import javax.annotation.Nullable;

@JsonDeserialize(as = OrderFromCartDraftImpl.class)
public interface OrderFromCartDraft {
    String getId();

    Long getVersion();

    @Nullable
    String getOrderNumber();

    @Nullable
    PaymentState getPaymentState();

    static OrderFromCartDraft of(final Versioned<Cart> cart, @Nullable final String orderNumber, @Nullable final PaymentState paymentState) {
        return new OrderFromCartDraftImpl(cart.getId(), cart.getVersion(), orderNumber, paymentState);
    }

    static OrderFromCartDraft of(final Versioned<Cart> cart) {
        return new OrderFromCartDraftImpl(cart.getId(), cart.getVersion(), null, null);
    }
}
