package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.carts.ExternalLineItemTotalPrice;
import io.sphere.sdk.carts.ItemShippingDetailsDraft;
import io.sphere.sdk.carts.LineItemDraft;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.products.ProductIdentifiable;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

public final class AddLineItem extends OrderEditStagedUpdateActionBase{

    private final String productId;
    private final Integer variantId;
    @Nullable
    private final String sku;
    private final Long quantity;
    @Nullable
    private final ResourceIdentifier<Channel> supplyChannel;
    @Nullable
    private final ResourceIdentifier<Channel> distributionChannel;
    @Nullable
    private final CustomFieldsDraft custom;
    @Nullable
    private final ExternalTaxRateDraft externalTaxRate;
    @Nullable
    private final MonetaryAmount externalPrice;
    @Nullable
    private final ExternalLineItemTotalPrice externalTotalPrice;
    @Nullable
    private final ItemShippingDetailsDraft shippingDetails;
    
    @JsonCreator
    private AddLineItem(@Nullable final String productId, @Nullable final Integer variantId, @Nullable String sku, final Long quantity, @Nullable final ResourceIdentifier<Channel> supplyChannel, @Nullable final ResourceIdentifier<Channel> distributionChannel, @Nullable final CustomFieldsDraft custom, @Nullable final ExternalTaxRateDraft externalTaxRate, @Nullable final MonetaryAmount externalPrice, @Nullable final ExternalLineItemTotalPrice externalTotalPrice, @Nullable final ItemShippingDetailsDraft shippingDetails) {
        super("addLineItem");
        this.productId = productId;
        this.variantId = variantId;
        this.sku = sku;
        this.quantity = quantity;
        this.supplyChannel = supplyChannel;
        this.distributionChannel = distributionChannel;
        this.custom = custom;
        this.externalTaxRate = externalTaxRate;
        this.externalPrice = externalPrice;
        this.externalTotalPrice = externalTotalPrice;
        this.shippingDetails = shippingDetails;
    }

    public static AddLineItem of(final ProductIdentifiable product, final int variantId, final long quantity) {
        return of(product.getId(), variantId, quantity);
    }

    public static AddLineItem of(final String productId, final int variantId, final long quantity) {
        return new AddLineItem(productId, variantId, null, quantity, null, null, null, null, null, null, null);
    }

    public static AddLineItem of(final LineItemDraft template) {
        return new AddLineItem(template.getProductId(), template.getVariantId(), template.getSku(), template.getQuantity(), template.getSupplyChannel(), template.getDistributionChannel(), template.getCustom(), template.getExternalTaxRate(), template.getExternalPrice(), template.getExternalTotalPrice(), template.getShippingDetails());
    }

    public String getProductId() {
        return productId;
    }

    @Nullable
    public String getSku() {
        return sku;
    }

    public Integer getVariantId() {
        return variantId;
    }

    public Long getQuantity() {
        return quantity;
    }

    @Nullable
    public ResourceIdentifier<Channel> getDistributionChannel() {
        return distributionChannel;
    }

    @Nullable
    public ResourceIdentifier<Channel> getSupplyChannel() {
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

    @Nullable
    public MonetaryAmount getExternalPrice() {
        return externalPrice;
    }

    @Nullable
    public ExternalLineItemTotalPrice getExternalTotalPrice() {
        return externalTotalPrice;
    }

    @Nullable
    public ItemShippingDetailsDraft getShippingDetails() {
        return shippingDetails;
    }
}