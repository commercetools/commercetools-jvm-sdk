package io.sphere.sdk.orders;

import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.*;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.taxcategories.TaxRate;

import java.util.List;
import java.util.Optional;

public class ImportLineItemBuilder extends Base implements Builder<ImportLineItem> {
    private Optional<String> productId = Optional.empty();
    private Optional<LocalizedStrings> name = Optional.empty();
    private ImportProductVariant variant;
    private Price price;
    private long quantity;
    private Optional<List<ItemState>> state = Optional.empty();
    private Optional<Reference<Channel>> supplyChannel = Optional.empty();
    private TaxRate taxRate;

    private ImportLineItemBuilder(final ImportProductVariant variant, final long quantity, final Price price, final TaxRate taxRate) {
        this.price = price;
        this.variant = variant;
        this.quantity = quantity;
        this.taxRate = taxRate;
    }

    public ImportLineItemBuilder productId(final Optional<String> productId) {
        this.productId = productId;
        return this;
    }

    public ImportLineItemBuilder productId(final String productId) {
        return productId(Optional.of(productId));
    }

    public ImportLineItemBuilder name(final Optional<LocalizedStrings> name) {
        this.name = name;
        return this;
    }

    public ImportLineItemBuilder name(final LocalizedStrings name) {
        return name(Optional.of(name));
    }

    public ImportLineItemBuilder variant(final ImportProductVariant variant) {
        this.variant = variant;
        return this;
    }

    public ImportLineItemBuilder price(final Price price) {
        this.price = price;
        return this;
    }

    public ImportLineItemBuilder quantity(final long quantity) {
        this.quantity = quantity;
        return this;
    }

    public ImportLineItemBuilder state(final Optional<List<ItemState>> state) {
        this.state = state;
        return this;
    }

    public ImportLineItemBuilder state(final List<ItemState> state) {
        return state(Optional.of(state));
    }

    public ImportLineItemBuilder supplyChannel(final Optional<Reference<Channel>> supplyChannel) {
        this.supplyChannel = supplyChannel;
        return this;
    }

    public ImportLineItemBuilder supplyChannel(final Referenceable<Channel> supplyChannel) {
        return supplyChannel(Optional.of(supplyChannel.toReference()));
    }

    public ImportLineItemBuilder taxRate(final TaxRate taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    public static ImportLineItemBuilder of(final ImportProductVariant variant, final long quantity, final Price price, final TaxRate taxRate) {
        return new ImportLineItemBuilder(variant, quantity, price, taxRate);
    }

    @Override
    public ImportLineItem build() {
        return new ImportLineItemImpl(name, productId, variant, price, quantity, state, supplyChannel, taxRate);
    }
}
