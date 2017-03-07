package io.sphere.sdk.orders;

import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.Set;

final class LineItemImportDraftImpl extends Base implements LineItemImportDraft {
    @Nullable
    private final String productId;
    private final LocalizedString name;
    private final ProductVariantImportDraft variant;
    private final Price price;
    private final Long quantity;
    @Nullable
    private final Set<ItemState> state;
    @Nullable
    private final Reference<Channel> supplyChannel;
    @Nullable
    private final TaxRate taxRate;
    @Nullable
    private final CustomFieldsDraft custom;

    public LineItemImportDraftImpl(final LocalizedString name, @Nullable final String productId, final ProductVariantImportDraft variant, final Price price, final Long quantity, @Nullable final Set<ItemState> state, @Nullable final Reference<Channel> supplyChannel, @Nullable final TaxRate taxRate, @Nullable final CustomFieldsDraft custom) {
        this.name = name;
        this.productId = productId;
        this.variant = variant;
        this.price = price;
        this.quantity = quantity;
        this.state = state;
        this.supplyChannel = supplyChannel;
        this.taxRate = taxRate;
        this.custom = custom;
    }

    @Override
    public LocalizedString getName() {
        return name;
    }

    @Override
    public Price getPrice() {
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
    public Reference<Channel> getSupplyChannel() {
        return supplyChannel;
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
}
