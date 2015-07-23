package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.cartdiscounts.DiscountedLineItemPrice;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.taxcategories.TaxRate;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

final class LineItemImpl extends LineItemLikeImpl implements LineItem {

    private final String productId;
    private final LocalizedStrings name;
    @Nullable
    private final LocalizedStrings productSlug;
    private final ProductVariant variant;
    private final Price price;
    private final Optional<TaxRate> taxRate;
    private final Optional<Reference<Channel>> supplyChannel;
    private final Optional<Reference<Channel>> distributionChannel;

    @JsonCreator
    LineItemImpl(final String id, final String productId, final LocalizedStrings name,
                 final ProductVariant variant, final Price price, final long quantity,
                 final Set<ItemState> state, final Optional<TaxRate> taxRate,
                 final Optional<Reference<Channel>> supplyChannel, final Optional<DiscountedLineItemPrice> discountedPrice,
                 final LocalizedStrings productSlug, final Optional<Reference<Channel>> distributionChannel) {
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
    public LocalizedStrings getName() {
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
    public Optional<TaxRate> getTaxRate() {
        return taxRate;
    }

    @Override
    public Optional<Reference<Channel>> getSupplyChannel() {
        return supplyChannel;
    }

    @Override
    public Optional<Reference<Channel>> getDistributionChannel() {
        return distributionChannel;
    }

    @Override
    @Nullable
    public LocalizedStrings getProductSlug() {
        return productSlug;
    }
}
