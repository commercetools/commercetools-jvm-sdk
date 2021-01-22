package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.CartDraftBuilder;
import io.sphere.sdk.carts.CartDraftDsl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.commands.updateactions.SetSku;
import io.sphere.sdk.states.State;
import io.sphere.sdk.zones.Zone;

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
     * @deprecated use ResourceIdentifier<Cart> getCart() instead
     */
    @Deprecated
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

    static OrderFromCartDraft of(final Cart cart, @Nullable final String orderNumber, @Nullable final PaymentState paymentState) {
        ResourceIdentifier<Cart> cartResourceIdentifier = ResourceIdentifier.ofId(cart.getId());

        return OrderFromCartDraftDsl.of(cartResourceIdentifier, cart.getVersion(), orderNumber, paymentState);
    }

    /**
     * getId has been @deprecated so this method has been replaced with {@link}
     */
    @Deprecated
    static OrderFromCartDraft of(final Versioned<Cart> cart, @Nullable final String orderNumber, @Nullable final PaymentState paymentState) {
        return OrderFromCartDraftDsl.of(cart.getId(), cart.getVersion(), orderNumber, paymentState);
    }

    static OrderFromCartDraft of(final Cart cart) {
        ResourceIdentifier<Cart> cartResourceIdentifier = ResourceIdentifier.ofId(cart.getId());

        return OrderFromCartDraftDsl.of(cartResourceIdentifier, cart.getVersion());
    }
    
    /**
     * getId has been @deprecated so this method has been replaced
     */
    @Deprecated
    static OrderFromCartDraft of(final Versioned<Cart> cart) {
        return OrderFromCartDraftDsl.of(cart.getId(), cart.getVersion(), null, null);
    }

}
