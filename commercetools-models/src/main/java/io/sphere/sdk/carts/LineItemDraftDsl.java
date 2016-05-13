package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.ProductIdentifiable;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;

public final class LineItemDraftDsl extends Base implements LineItemDraft {

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
    @Nullable
    private final ExternalTaxRateDraft externalTaxRate;

    @JsonCreator
    LineItemDraftDsl(final String productId, final Integer variantId, @Nullable final Long quantity, @Nullable final Reference<Channel> supplyChannel, @Nullable final Reference<Channel> distributionChannel, @Nullable final CustomFieldsDraft custom, final ExternalTaxRateDraft externalTaxRate) {
        this.custom = custom;
        this.productId = productId;
        this.variantId = variantId;
        this.quantity = quantity;
        this.supplyChannel = supplyChannel;
        this.distributionChannel = distributionChannel;
        this.externalTaxRate = externalTaxRate;
    }

    public static LineItemDraftDsl of(final ProductIdentifiable product, final Integer variantId, final long quantity) {
        return of(product.getId(), variantId, quantity);
    }

    public static LineItemDraftDsl of(final String productId, final Integer variantId, final long quantity) {
        return new LineItemDraftDsl(productId, variantId, quantity, null, null, null, null);
    }

    @Override
    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }

    @Override
    @Nullable
    public Reference<Channel> getDistributionChannel() {
        return distributionChannel;
    }

    @Override
    public String getProductId() {
        return productId;
    }

    @Override
    @Nullable
    public Long getQuantity() {
        return quantity;
    }

    @Override
    @Nullable
    public Reference<Channel> getSupplyChannel() {
        return supplyChannel;
    }

    @Override
    public Integer getVariantId() {
        return variantId;
    }

    @Override
    @Nullable
    public ExternalTaxRateDraft getExternalTaxRate() {
        return externalTaxRate;
    }

    public LineItemDraftDsl withSupplyChannel(final Referenceable<Channel> supplyChannel) {
        return new LineItemDraftDsl(getProductId(), getVariantId(), getQuantity(), supplyChannel.toReference(), getDistributionChannel(), getCustom(), externalTaxRate);
    }

    public LineItemDraftDsl withDistributionChannel(final Referenceable<Channel> distributionChannel) {
        return new LineItemDraftDsl(getProductId(), getVariantId(), getQuantity(), getSupplyChannel(), distributionChannel.toReference(), getCustom(), externalTaxRate);
    }

    public LineItemDraftDsl withCustom(final CustomFieldsDraft custom) {
        return new LineItemDraftDsl(getProductId(), getVariantId(), getQuantity(), getSupplyChannel(), getDistributionChannel(), custom, externalTaxRate);
    }

    public LineItemDraftDsl withExternalTaxRate(final ExternalTaxRateDraft externalTaxRate) {
        return new LineItemDraftDsl(getProductId(), getVariantId(), getQuantity(), getSupplyChannel(), getDistributionChannel(), getCustom(), externalTaxRate);
    }
}
