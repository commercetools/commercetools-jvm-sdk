package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.ExternalLineItemTotalPrice;
import io.sphere.sdk.carts.ItemShippingDetailsDraft;
import io.sphere.sdk.carts.LineItemDraft;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.products.ProductIdentifiable;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

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
    @Nullable
    private final ZonedDateTime addedAt;

    private AddLineItem(@Nullable final String productId, @Nullable final Integer variantId, @Nullable String sku, final Long quantity, @Nullable final ResourceIdentifier<Channel> supplyChannel, @Nullable final ResourceIdentifier<Channel> distributionChannel, @Nullable final CustomFieldsDraft custom, @Nullable final ExternalTaxRateDraft externalTaxRate, @Nullable final MonetaryAmount externalPrice, @Nullable final ExternalLineItemTotalPrice externalTotalPrice, @Nullable final ItemShippingDetailsDraft shippingDetails, @Nullable final ZonedDateTime addedAt) {
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
        this.addedAt = addedAt;
    }

    public static AddLineItem of(final ProductIdentifiable product, final int variantId, final long quantity) {
        return of(product.getId(), variantId, quantity);
    }

    public static AddLineItem of(final String productId, final int variantId, final long quantity) {
        return new AddLineItem(productId, variantId, null, quantity, null, null, null, null, null, null, null, null);
    }

    public static AddLineItem of(final LineItemDraft template) {
        return new AddLineItem(template.getProductId(), template.getVariantId(), template.getSku(), template.getQuantity(), template.getSupplyChannel(), template.getDistributionChannel(), template.getCustom(), template.getExternalTaxRate(), template.getExternalPrice(), template.getExternalTotalPrice(), template.getShippingDetails(), template.getAddedAt());
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

    @Nullable
    public ZonedDateTime getAddedAt() {
        return addedAt;
    }
    
    public AddLineItem withSupplyChannel(final Referenceable<Channel> supplyChannel) {
        return new AddLineItem(getProductId(), getVariantId(), getSku(), getQuantity(), supplyChannel.toReference(), getDistributionChannel(), getCustom(), getExternalTaxRate(), getExternalPrice(), getExternalTotalPrice(), getShippingDetails(), getAddedAt());
    }

    public AddLineItem withDistributionChannel(final Referenceable<Channel> distributionChannel) {
        return new AddLineItem(getProductId(), getVariantId(), getSku(), getQuantity(), getSupplyChannel(), distributionChannel.toReference(), getCustom(), getExternalTaxRate(), getExternalPrice(), getExternalTotalPrice(), getShippingDetails(), getAddedAt());
    }

    public AddLineItem withCustom(final CustomFieldsDraft custom) {
        return new AddLineItem(getProductId(), getVariantId(), getSku(), getQuantity(), getSupplyChannel(), getDistributionChannel(), custom, getExternalTaxRate(), getExternalPrice(), getExternalTotalPrice(), getShippingDetails(), getAddedAt());
    }

    public AddLineItem withExternalTaxRate(@Nullable final ExternalTaxRateDraft externalTaxRate) {
        return new AddLineItem(getProductId(), getVariantId(), getSku(), getQuantity(), getSupplyChannel(), getDistributionChannel(), getCustom(), externalTaxRate, getExternalPrice(), getExternalTotalPrice(), getShippingDetails(), getAddedAt());
    }

    public AddLineItem withExternalPrice(@Nullable final MonetaryAmount externalPrice) {
        return new AddLineItem(getProductId(), getVariantId(), getSku(), getQuantity(), getSupplyChannel(), getDistributionChannel(), getCustom(), getExternalTaxRate(), externalPrice, getExternalTotalPrice(), getShippingDetails(), getAddedAt());
    }

    public AddLineItem withExternalTotalPrice(@Nullable final ExternalLineItemTotalPrice externalTotalPrice) {
        return new AddLineItem(getProductId(), getVariantId(), getSku(), getQuantity(), getSupplyChannel(), getDistributionChannel(), getCustom(), getExternalTaxRate(), getExternalPrice(), externalTotalPrice, getShippingDetails(), getAddedAt());
    }
}
