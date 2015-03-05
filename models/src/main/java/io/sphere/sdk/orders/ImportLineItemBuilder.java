package io.sphere.sdk.orders;

import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.*;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.taxcategories.TaxRate;

import java.util.List;
import java.util.Optional;

public class ImportLineItemBuilder extends Base implements Builder<ImportLineItem> {
    private final Optional<String> productId;
    private final LocalizedStrings name;
    private final ImportProductVariant variant;
    private Price price;
    private long quantity;
    private Optional<List<ItemState>> state = Optional.empty();
    private Optional<Reference<Channel>> supplyChannel = Optional.empty();
    private Optional<TaxRate> taxRate = Optional.empty();

    private ImportLineItemBuilder(final ImportProductVariant variant, final long quantity, final Price price, final Optional<String> productId, final LocalizedStrings name) {
        this.price = price;
        this.variant = variant;
        this.quantity = quantity;
        this.productId = productId;
        this.name = name;
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

    public ImportLineItemBuilder taxRate(final Optional<TaxRate> taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    public ImportLineItemBuilder taxRate(final TaxRate taxRate) {
        return taxRate(Optional.of(taxRate));
    }

    public static ImportLineItemBuilder of(final ImportProductVariant variant, final long quantity, final Price price, final LocalizedStrings name) {
        return new ImportLineItemBuilder(variant, quantity, price, variant.getProductId(), name);
    }

    @Override
    public ImportLineItem build() {
        return new ImportLineItemImpl(name, productId, variant, price, quantity, state, supplyChannel, taxRate);
    }
}
