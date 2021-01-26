package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;

@JsonDeserialize(as = OrderFromCartDraftDsl.class)
@ResourceDraftValue(factoryMethods =
        {
                @FactoryMethod(parameterNames = {"id", "version", "orderNumber", "paymentState"}),
                @FactoryMethod(parameterNames = {"id", "version"}),
                @FactoryMethod(parameterNames = {"cart", "version", "orderNumber", "paymentState"}),
                @FactoryMethod(parameterNames = {"cart", "version"})
        })
public interface OrderFromCartDraft {

    /**
     * @deprecated use {@link OrderFromCartDraft#getCart()} instead
     */
    @Deprecated
    @Nullable
    String getId();

    ResourceIdentifier<Cart> getCart();

    Long getVersion();

    @Nullable
    String getOrderNumber();

    @Nullable
    PaymentState getPaymentState();

    @Nullable
    OrderState getOrderState();

    @Nullable
    Reference<State> getState();

    @Nullable
    ShipmentState getShipmentState();

    static OrderFromCartDraft of(final Versioned<Cart> cart, @Nullable final String orderNumber, @Nullable final PaymentState paymentState) {
        return OrderFromCartDraftDsl.of(ResourceIdentifier.ofId(cart.getId()), cart.getVersion(), orderNumber, paymentState);
    }

    static OrderFromCartDraft of(final Versioned<Cart> cart) {
        return OrderFromCartDraftDsl.of(ResourceIdentifier.ofId(cart.getId()), cart.getVersion(), null, null);
    }

    static OrderFromCartDraft of(final ResourceIdentifier<Cart> cartResourceIdentifier, final Long version, @Nullable final String orderNumber, @Nullable final PaymentState paymentState) {
        return OrderFromCartDraftDsl.of(cartResourceIdentifier, version, orderNumber, paymentState);
    }

    static OrderFromCartDraft of(final ResourceIdentifier<Cart> cartResourceIdentifier, final Long version) {
        return OrderFromCartDraftDsl.of(cartResourceIdentifier, version, null, null);
    }
}
