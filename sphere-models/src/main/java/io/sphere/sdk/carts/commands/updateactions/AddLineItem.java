package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.ProductIdentifiable;

import javax.annotation.Nullable;

/**
 Adds a product variant in the given quantity to the cart.
 If the cart already contains the product variant then only the line item quantity is increased.

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandTest#addLineItem()}
 */
public class AddLineItem extends UpdateAction<Cart> {
    private final String productId;
    private final Integer variantId;
    private final long quantity;
    @Nullable
    private final Reference<Channel> supplyChannel;
    @Nullable private final Reference<Channel> distributionChannel;

    private AddLineItem(final String productId, final Integer variantId, final long quantity, final Reference<Channel> supplyChannel, final Reference<Channel> distributionChannel) {
        super("addLineItem");
        this.productId = productId;
        this.variantId = variantId;
        this.quantity = quantity;
        this.supplyChannel = supplyChannel;
        this.distributionChannel = distributionChannel;
    }

    public static AddLineItem of(final ProductIdentifiable product, final Integer variantId, final long quantity) {
        return of(product.getId(), variantId, quantity);
    }

    public static AddLineItem of(final String productId, final Integer variantId, final long quantity) {
        return new AddLineItem(productId, variantId, quantity, null, null);
    }

    public String getProductId() {
        return productId;
    }

    public Integer getVariantId() {
        return variantId;
    }

    public long getQuantity() {
        return quantity;
    }

    @Nullable
    public Reference<Channel> getDistributionChannel() {
        return distributionChannel;
    }

    @Nullable
    public Reference<Channel> getSupplyChannel() {
        return supplyChannel;
    }

    public AddLineItem withSupplyChannel(final Referenceable<Channel> supplyChannel) {
        return new AddLineItem(getProductId(), getVariantId(), getQuantity(), supplyChannel.toReference(), getDistributionChannel());
    }

    public AddLineItem withDistributionChannel(final Referenceable<Channel> distributionChannel) {
        return new AddLineItem(getProductId(), getVariantId(), getQuantity(), getSupplyChannel(), distributionChannel.toReference());
    }
}
