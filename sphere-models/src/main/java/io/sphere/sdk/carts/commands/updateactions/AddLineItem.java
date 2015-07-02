package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.ProductIdentifiable;

import java.util.Optional;

/**
 Adds a product variant in the given quantity to the cart.
 If the cart already contains the product variant then only the line item quantity is increased.

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandTest#addLineItem()}
 */
public class AddLineItem extends UpdateAction<Cart> {
    private final String productId;
    private final int variantId;
    private final long quantity;
    private final Optional<Reference<Channel>> supplyChannel;
    private final Optional<Reference<Channel>> distributionChannel;

    private AddLineItem(final String productId, final int variantId, final long quantity, final Optional<Reference<Channel>> supplyChannel, final Optional<Reference<Channel>> distributionChannel) {
        super("addLineItem");
        this.productId = productId;
        this.variantId = variantId;
        this.quantity = quantity;
        this.supplyChannel = supplyChannel;
        this.distributionChannel = distributionChannel;
    }

    public static AddLineItem of(final ProductIdentifiable product, final int variantId, final long quantity) {
        return of(product.getId(), variantId, quantity);
    }

    public static AddLineItem of(final String productId, final int variantId, final long quantity) {
        return new AddLineItem(productId, variantId, quantity, Optional.empty(), Optional.empty());
    }

    public String getProductId() {
        return productId;
    }

    public int getVariantId() {
        return variantId;
    }

    public long getQuantity() {
        return quantity;
    }

    public Optional<Reference<Channel>> getDistributionChannel() {
        return distributionChannel;
    }

    public Optional<Reference<Channel>> getSupplyChannel() {
        return supplyChannel;
    }

    public AddLineItem withSupplyChannel(final Referenceable<Channel> supplyChannel) {
        return new AddLineItem(getProductId(), getVariantId(), getQuantity(), Optional.of(supplyChannel.toReference()), getDistributionChannel());
    }

    public AddLineItem withDistributionChannel(final Referenceable<Channel> distributionChannel) {
        return new AddLineItem(getProductId(), getVariantId(), getQuantity(), getSupplyChannel(), Optional.of(distributionChannel.toReference()));
    }
}
