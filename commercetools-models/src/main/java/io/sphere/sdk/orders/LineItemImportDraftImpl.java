package io.sphere.sdk.orders;

import io.sphere.sdk.carts.ItemShippingDetailsDraft;
import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.Set;

final class LineItemImportDraftImpl extends Base implements LineItemImportDraft {
    @Nullable
    private final String productId;
    private final LocalizedString name;
    private final ProductVariantImportDraft variant;
    private final PriceDraft price;
    private final Long quantity;
    @Nullable
    private final Set<ItemState> state;
    @Nullable
    private final ResourceIdentifier<Channel> supplyChannel;
    @Nullable
    private final ResourceIdentifier<Channel> distributionChannel;
    @Nullable
    private final TaxRate taxRate;
    @Nullable
    private final CustomFieldsDraft custom;


    @Nullable private final ItemShippingDetailsDraft shippingDetails;

    public LineItemImportDraftImpl(final LocalizedString name, @Nullable final String productId, final ProductVariantImportDraft variant, final PriceDraft price, final Long quantity, @Nullable final Set<ItemState> state, @Nullable final ResourceIdentifier<Channel> supplyChannel,final ResourceIdentifier<Channel> distributionChannel, @Nullable final TaxRate taxRate, @Nullable final CustomFieldsDraft custom, @Nullable final ItemShippingDetailsDraft shippingDetails) {
        this.name = name;
        this.productId = productId;
        this.variant = variant;
        this.price = price;
        this.quantity = quantity;
        this.state = state;
        this.supplyChannel = supplyChannel;
        this.taxRate = taxRate;
        this.custom = custom;
        this.shippingDetails = shippingDetails;
        this.distributionChannel = distributionChannel;
    }

    @Override
    public LocalizedString getName() {
        return name;
    }

    @Override
    public PriceDraft getPrice() {
        return price;
    }

    @Nullable
    @Override
    public String getProductId() {
        return productId;
    }

    @Override
    public Long getQuantity() {
        return quantity;
    }

    @Override
    @Nullable
    public Set<ItemState> getState() {
        return state;
    }

    @Override
    @Nullable
    public ResourceIdentifier<Channel> getSupplyChannel() {
        return supplyChannel;
    }

    @Nullable
    @Override
    public ResourceIdentifier<Channel> getDistributionChannel() {
        return distributionChannel;
    }

    @Override
    @Nullable
    public TaxRate getTaxRate() {
        return taxRate;
    }

    @Override
    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }

    @Override
    public ProductVariantImportDraft getVariant() {
        return variant;
    }

    @Nullable
    @Override
    public ItemShippingDetailsDraft getShippingDetails() {
        return shippingDetails;
    }
}
