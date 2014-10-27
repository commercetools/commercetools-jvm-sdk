package io.sphere.sdk.carts;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.taxcategories.TaxRate;

import java.util.List;
import java.util.Optional;

final class LineItemImpl extends Base implements LineItem {
    private final String id;
    private final String productId;
    private final LocalizedString name;
    private final ProductVariant variant;
    private final Price price;
    private final long quantity;
    private final List<ItemState> state;
    private final TaxRate taxRate;
    private final Optional<Channel> supplyChannel;

    LineItemImpl(final String id, final String productId, final LocalizedString name,
                 final ProductVariant variant, final Price price, final long quantity,
                 final List<ItemState> state, final TaxRate taxRate, final Optional<Channel> supplyChannel) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.variant = variant;
        this.price = price;
        this.quantity = quantity;
        this.state = state;
        this.taxRate = taxRate;
        this.supplyChannel = supplyChannel;
    }

    @Override
    public String getId() {
        return id;
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
    public long getQuantity() {
        return quantity;
    }

    @Override
    public List<ItemState> getState() {
        return state;
    }

    @Override
    public TaxRate getTaxRate() {
        return taxRate;
    }

    @Override
    public Optional<Channel> getSupplyChannel() {
        return supplyChannel;
    }
}
