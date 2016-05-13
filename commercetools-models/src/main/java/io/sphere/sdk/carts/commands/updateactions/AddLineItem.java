package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.ProductIdentifiable;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;

/**
    Adds a product variant in the given quantity to the cart.
    If the cart already contains the product variant then only the line item quantity is increased.

    {@doc.gen intro}

    {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#addLineItem()}

 @see Cart#getLineItems()
 @see io.sphere.sdk.orders.Order#getLineItems()
 @see RemoveLineItem
 @see ChangeLineItemQuantity
 */
public final class AddLineItem extends UpdateActionImpl<Cart> implements CustomDraft {
    private final String productId;
    private final Integer variantId;
    private final Long quantity;
    @Nullable
    private final Reference<Channel> supplyChannel;
    @Nullable
    private final Reference<Channel> distributionChannel;
    @Nullable
    private final CustomFieldsDraft custom;
    @Nullable
    private final ExternalTaxRateDraft externalTaxRate;

    private AddLineItem(final String productId, final Integer variantId, final Long quantity, @Nullable final Reference<Channel> supplyChannel, @Nullable final Reference<Channel> distributionChannel, @Nullable final CustomFieldsDraft custom, @Nullable final ExternalTaxRateDraft externalTaxRate) {
        super("addLineItem");
        this.productId = productId;
        this.variantId = variantId;
        this.quantity = quantity;
        this.supplyChannel = supplyChannel;
        this.distributionChannel = distributionChannel;
        this.custom = custom;
        this.externalTaxRate = externalTaxRate;
    }

    public static AddLineItem of(final ProductIdentifiable product, final int variantId, final long quantity) {
        return of(product.getId(), variantId, quantity);
    }

    public static AddLineItem of(final String productId, final int variantId, final long quantity) {
        return new AddLineItem(productId, variantId, quantity, null, null, null, null);
    }

    public String getProductId() {
        return productId;
    }

    public Integer getVariantId() {
        return variantId;
    }

    public Long getQuantity() {
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

    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }

    @Nullable
    public ExternalTaxRateDraft getExternalTaxRate() {
        return externalTaxRate;
    }

    public AddLineItem withSupplyChannel(final Referenceable<Channel> supplyChannel) {
        return new AddLineItem(getProductId(), getVariantId(), getQuantity(), supplyChannel.toReference(), getDistributionChannel(), getCustom(), getExternalTaxRate());
    }

    public AddLineItem withDistributionChannel(final Referenceable<Channel> distributionChannel) {
        return new AddLineItem(getProductId(), getVariantId(), getQuantity(), getSupplyChannel(), distributionChannel.toReference(), getCustom(), getExternalTaxRate());
    }

    public AddLineItem withCustom(final CustomFieldsDraft custom) {
        return new AddLineItem(getProductId(), getVariantId(), getQuantity(), getSupplyChannel(), getDistributionChannel(), custom, getExternalTaxRate());
    }

    public AddLineItem withExternalTaxRate(@Nullable final ExternalTaxRateDraft externalTaxRate) {
        return new AddLineItem(getProductId(), getVariantId(), getQuantity(), getSupplyChannel(), getDistributionChannel(), getCustom(), externalTaxRate);
    }
}
