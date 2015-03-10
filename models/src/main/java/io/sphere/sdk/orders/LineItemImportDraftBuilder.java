package io.sphere.sdk.orders;

import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.*;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.taxcategories.TaxRate;

import java.util.Optional;
import java.util.Set;

public class LineItemImportDraftBuilder extends Base implements Builder<LineItemImportDraft> {
    private final Optional<String> productId;
    private final LocalizedStrings name;
    private final ProductVariantImportDraft variant;
    private Price price;
    private long quantity;
    private Optional<Set<ItemState>> state = Optional.empty();
    private Optional<Reference<Channel>> supplyChannel = Optional.empty();
    private Optional<TaxRate> taxRate = Optional.empty();

    private LineItemImportDraftBuilder(final ProductVariantImportDraft variant, final long quantity, final Price price, final Optional<String> productId, final LocalizedStrings name) {
        this.price = price;
        this.variant = variant;
        this.quantity = quantity;
        this.productId = productId;
        this.name = name;
    }

    public LineItemImportDraftBuilder price(final Price price) {
        this.price = price;
        return this;
    }

    public LineItemImportDraftBuilder quantity(final long quantity) {
        this.quantity = quantity;
        return this;
    }

    public LineItemImportDraftBuilder state(final Optional<Set<ItemState>> state) {
        this.state = state;
        return this;
    }

    public LineItemImportDraftBuilder state(final Set<ItemState> state) {
        return state(Optional.of(state));
    }

    public LineItemImportDraftBuilder supplyChannel(final Optional<Reference<Channel>> supplyChannel) {
        this.supplyChannel = supplyChannel;
        return this;
    }

    public LineItemImportDraftBuilder supplyChannel(final Referenceable<Channel> supplyChannel) {
        return supplyChannel(Optional.of(supplyChannel.toReference()));
    }

    public LineItemImportDraftBuilder taxRate(final Optional<TaxRate> taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    public LineItemImportDraftBuilder taxRate(final TaxRate taxRate) {
        return taxRate(Optional.of(taxRate));
    }

    public static LineItemImportDraftBuilder of(final ProductVariantImportDraft variant, final long quantity, final Price price, final LocalizedStrings name) {
        return new LineItemImportDraftBuilder(variant, quantity, price, variant.getProductId(), name);
    }

    @Override
    public LineItemImportDraft build() {
        return new LineItemImportDraftImpl(name, productId, variant, price, quantity, state, supplyChannel, taxRate);
    }
}
