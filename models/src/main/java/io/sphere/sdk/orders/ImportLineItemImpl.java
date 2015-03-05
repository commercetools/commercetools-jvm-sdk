package io.sphere.sdk.orders;

import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.taxcategories.TaxRate;

import java.util.List;
import java.util.Optional;

final class ImportLineItemImpl extends Base implements ImportLineItem {
    private final Optional<String> productId;
    private final Optional<LocalizedStrings> name;
    private final ImportProductVariant variant;
    private final Price price;
    private final long quantity;
    private final Optional<List<ItemState>> state;
    private final Optional<Reference<Channel>> supplyChannel;
    private final Optional<TaxRate> taxRate;

    public ImportLineItemImpl(final Optional<LocalizedStrings> name, final Optional<String> productId, final ImportProductVariant variant, final Price price, final long quantity, final Optional<List<ItemState>> state, final Optional<Reference<Channel>> supplyChannel, final Optional<TaxRate> taxRate) {
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
    public Optional<LocalizedStrings> getName() {
        return name;
    }

    @Override
    public Price getPrice() {
        return price;
    }

    @Override
    public Optional<String> getProductId() {
        return productId;
    }

    @Override
    public long getQuantity() {
        return quantity;
    }

    @Override
    public Optional<List<ItemState>> getState() {
        return state;
    }

    @Override
    public Optional<Reference<Channel>> getSupplyChannel() {
        return supplyChannel;
    }

    @Override
    public Optional<TaxRate> getTaxRate() {
        return taxRate;
    }

    @Override
    public ImportProductVariant getVariant() {
        return variant;
    }
}
