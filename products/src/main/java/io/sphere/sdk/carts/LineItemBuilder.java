package io.sphere.sdk.carts;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.taxcategories.TaxRate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class LineItemBuilder extends Base implements Builder<LineItem> {
    private final String id;
    private final String productId;
    private final LocalizedString name;
    private final ProductVariant variant;
    private final Price price;
    private final long quantity;
    private List<ItemState> state = Collections.emptyList();
    private final TaxRate taxRate;
    private Optional<Channel> supplyChannel = Optional.empty();

    private LineItemBuilder(final String id, final String productId, final LocalizedString name, final ProductVariant variant, final Price price, final long quantity, final TaxRate taxRate) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.variant = variant;
        this.price = price;
        this.quantity = quantity;
        this.taxRate = taxRate;
    }

    public LineItemBuilder state(final List<ItemState> state) {
        this.state = state;
        return this;
    }

    public LineItemBuilder supplyChannel(final Optional<Channel> supplyChannel) {
        this.supplyChannel = supplyChannel;
        return this;
    }

    public static LineItemBuilder of(final String id, final String productId, final LocalizedString name, final ProductVariant variant, final Price price, final long quantity, final TaxRate taxRate) {
        return new LineItemBuilder(id, productId, name, variant, price, quantity, taxRate);
    }

    @Override
    public LineItem build() {
        return new LineItemImpl(id, productId, name, variant, price, quantity, state, taxRate, supplyChannel);
    }
}
