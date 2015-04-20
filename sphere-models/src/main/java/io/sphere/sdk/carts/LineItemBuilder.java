package io.sphere.sdk.carts;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.*;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.taxcategories.TaxRate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class LineItemBuilder extends Base implements Builder<LineItem> {
    private final String id;
    private final String productId;
    private final LocalizedStrings name;
    private final ProductVariant variant;
    private final Price price;
    private final long quantity;
    private Set<ItemState> state = Collections.emptySet();
    private Optional<TaxRate> taxRate = Optional.empty();
    private Optional<Reference<Channel>> supplyChannel = Optional.empty();

    private LineItemBuilder(final String id, final String productId, final LocalizedStrings name, final ProductVariant variant, final Price price, final long quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.variant = variant;
        this.price = price;
        this.quantity = quantity;
    }

    public LineItemBuilder state(final Set<ItemState> state) {
        this.state = state;
        return this;
    }

    public LineItemBuilder supplyChannel(final Referenceable<Channel> supplyChannel) {
        return supplyChannel(Optional.of(supplyChannel.toReference()));
    }

    public LineItemBuilder supplyChannel(final Optional<Reference<Channel>> supplyChannel) {
        this.supplyChannel = supplyChannel;
        return this;
    }

    public LineItemBuilder taxRate(final TaxRate taxRate) {
        return taxRate(Optional.of(taxRate));
    }

    public LineItemBuilder taxRate(final Optional<TaxRate> taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    @Deprecated
    /**
     *  Use {@link #of(String, String, LocalizedStrings, ProductVariant, Price, long)} and {@link #taxRate(TaxRate)} instead.
     */
    public static LineItemBuilder of(final String id, final String productId, final LocalizedStrings name, final ProductVariant variant, final Price price, final long quantity, final TaxRate taxRate) {
        return of(id, productId, name, variant, price, quantity).taxRate(taxRate);
    }

    public static LineItemBuilder of(final String id, final String productId, final LocalizedStrings name, final ProductVariant variant, final Price price, final long quantity) {
        return new LineItemBuilder(id, productId, name, variant, price, quantity);
    }

    @Override
    public LineItem build() {
        return new LineItemImpl(id, productId, name, variant, price, quantity, state, taxRate, supplyChannel);
    }
}
