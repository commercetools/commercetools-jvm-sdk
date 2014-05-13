package io.sphere.client.shop;

import com.google.common.base.Optional;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.model.PaymentState;
import io.sphere.internal.command.CartCommands;
import net.jcip.annotations.NotThreadSafe;

/**
 * Builder to create data for creating an order.
 */
@NotThreadSafe
public class CreateOrderBuilder {
    private VersionedId cartId;
    private PaymentState paymentState;
    private Optional<String> cartSnapshotId = Optional.absent();
    private Optional<String> orderNumber = Optional.absent();

    public CreateOrderBuilder(VersionedId cartId, PaymentState paymentState) {
        this.cartId = cartId;
        this.paymentState = paymentState;
    }

    public VersionedId getCartId() { return cartId; }

    public PaymentState getPaymentState() { return paymentState; }

    public Optional<String> getCartSnapshotId() { return cartSnapshotId; }

    public Optional<String> getOrderNumber() { return orderNumber; }

    public CreateOrderBuilder setCartSnapshotId(final String cartSnapshotId) {
        this.cartSnapshotId = Optional.fromNullable(cartSnapshotId);
        return this;
    }

    public CreateOrderBuilder setOrderNumber(final String orderNumber) {
        this.orderNumber = Optional.fromNullable(orderNumber);
        return this;
    }

    public CartCommands.OrderCart build() {
        return new CartCommands.OrderCart(cartId.getId(), cartId.getVersion(), paymentState, orderNumber);
    }
}
