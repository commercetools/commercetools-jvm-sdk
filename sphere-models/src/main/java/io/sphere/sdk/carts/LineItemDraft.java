package io.sphere.sdk.carts;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.ProductIdentifiable;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;

/**
 * Draft for a new line item.
 *
 * @see io.sphere.sdk.carts.commands.CartCreateCommand
 */
public final class LineItemDraft extends Base {

    private final String productId;
    private final Integer variantId;
    @Nullable
    private final Long quantity;
    @Nullable
    private final Reference<Channel> supplyChannel;
    @Nullable
    private final Reference<Channel> distributionChannel;
    @Nullable
    private final CustomFieldsDraft custom;

    private LineItemDraft(final String productId, final Integer variantId, @Nullable final Long quantity, @Nullable final Reference<Channel> supplyChannel, @Nullable final Reference<Channel> distributionChannel, @Nullable final CustomFieldsDraft custom) {
        this.custom = custom;
        this.productId = productId;
        this.variantId = variantId;
        this.quantity = quantity;
        this.supplyChannel = supplyChannel;
        this.distributionChannel = distributionChannel;
    }

    public static LineItemDraft of(final ProductIdentifiable product, final Integer variantId, final long quantity) {
        return of(product.getId(), variantId, quantity);
    }

    public static LineItemDraft of(final String productId, final Integer variantId, final long quantity) {
        return new LineItemDraft(productId, variantId, quantity, null, null, null);
    }

    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }

    @Nullable
    public Reference<Channel> getDistributionChannel() {
        return distributionChannel;
    }

    public String getProductId() {
        return productId;
    }

    @Nullable
    public Long getQuantity() {
        return quantity;
    }

    @Nullable
    public Reference<Channel> getSupplyChannel() {
        return supplyChannel;
    }

    public Integer getVariantId() {
        return variantId;
    }


    public LineItemDraft withSupplyChannel(final Referenceable<Channel> supplyChannel) {
        return new LineItemDraft(getProductId(), getVariantId(), getQuantity(), supplyChannel.toReference(), getDistributionChannel(), getCustom());
    }

    public LineItemDraft withDistributionChannel(final Referenceable<Channel> distributionChannel) {
        return new LineItemDraft(getProductId(), getVariantId(), getQuantity(), getSupplyChannel(), distributionChannel.toReference(), getCustom());
    }

    public LineItemDraft withCustom(final CustomFieldsDraft custom) {
        return new LineItemDraft(getProductId(), getVariantId(), getQuantity(), getSupplyChannel(), getDistributionChannel(), custom);
    }
}
