package io.sphere.sdk.orders;

import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.taxcategories.TaxRate;

import javax.annotation.Nullable;
import java.util.Set;

final class LineItemImportDraftImpl extends Base implements LineItemImportDraft {
    @Nullable
    private final String productId;
    private final LocalizedStrings name;
    private final ProductVariantImportDraft variant;
    private final Price price;
    private final long quantity;
    @Nullable
    private final Set<ItemState> state;
    @Nullable
    private final Reference<Channel> supplyChannel;
    @Nullable
    private final TaxRate taxRate;

    public LineItemImportDraftImpl(final LocalizedStrings name, final String productId, final ProductVariantImportDraft variant, final Price price, final long quantity, final Set<ItemState> state, final Reference<Channel> supplyChannel, final TaxRate taxRate) {
        this.name = name;
        this.productId = productId;
        this.variant = variant;
        this.price = price;
        this.quantity = quantity;
        this.state = state;
        this.supplyChannel = supplyChannel;
        this.taxRate = taxRate;
    }

    @Override
    public LocalizedStrings getName() {
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
    public ProductVariantImportDraft getVariant() {
        return variant;
    }
}
