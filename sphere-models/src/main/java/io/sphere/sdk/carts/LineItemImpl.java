package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.cartdiscounts.DiscountedLineItemPrice;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.taxcategories.TaxRate;

import javax.annotation.Nullable;
import java.util.Set;

final class LineItemImpl extends LineItemLikeImpl implements LineItem {

    private final String productId;
    private final LocalizedString name;
    @Nullable
    private final LocalizedString productSlug;
    private final ProductVariant variant;
    private final Price price;
    @Nullable
    private final TaxRate taxRate;
    @Nullable
    private final Reference<Channel> supplyChannel;
    @Nullable
    private final Reference<Channel> distributionChannel;

    @JsonCreator
    LineItemImpl(final String id, final String productId, final LocalizedString name,
                 final ProductVariant variant, final Price price, final Long quantity,
                 final Set<ItemState> state, final TaxRate taxRate,
                 final Reference<Channel> supplyChannel, final DiscountedLineItemPrice discountedPrice,
                 final LocalizedString productSlug, final Reference<Channel> distributionChannel) {
        super(id, state, quantity, discountedPrice);
        this.productId = productId;
        this.name = name;
        this.variant = variant;
        this.price = price;
        this.taxRate = taxRate;
        this.supplyChannel = supplyChannel;
        this.productSlug = productSlug;
        this.distributionChannel = distributionChannel;
    }

    @Override
    public String getProductId() {
        return productId;
    }

    @Override
    public LocalizedString getName() {
        return name;
    }

    @Override
    public ProductVariant getVariant() {
        return variant;
    }

    @Override
    public Price getPrice() {
        return price;
    }

    @Override
    @Nullable
    public TaxRate getTaxRate() {
        return taxRate;
    }

    @Nullable
    @Override
    public Reference<Channel> getSupplyChannel() {
        return supplyChannel;
    }

    @Nullable
    @Override
    public Reference<Channel> getDistributionChannel() {
        return distributionChannel;
    }

    @Override
    @Nullable
    public LocalizedString getProductSlug() {
        return productSlug;
    }
}
