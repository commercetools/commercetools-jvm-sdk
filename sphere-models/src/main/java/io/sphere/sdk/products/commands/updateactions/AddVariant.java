package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.attributes.AttributeDraft;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Adds a variant to a product.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#addVariant()}
 *
 * @see io.sphere.sdk.products.commands.updateactions.RemoveVariant
 */
public class AddVariant extends UpdateActionImpl<Product> {
    @Nullable
    private final String sku;
    private final List<PriceDraft> prices;
    private final List<AttributeDraft> attributes;

    private AddVariant(final List<AttributeDraft> attributes, final List<PriceDraft> prices, @Nullable final String sku) {
        super("addVariant");
        this.attributes = attributes;
        this.prices = prices;
        this.sku = sku;
    }

    public List<AttributeDraft> getAttributes() {
        return attributes;
    }

    public List<PriceDraft> getPrices() {
        return prices;
    }

    @Nullable
    public String getSku() {
        return sku;
    }

    public static AddVariant of(final List<AttributeDraft> attributes, final List<PriceDraft> prices, @Nullable final String sku) {
        return new AddVariant(attributes, prices, sku);
    }

    public static AddVariant of(final List<AttributeDraft> attributes, final List<PriceDraft> prices) {
        return new AddVariant(attributes, prices, null);
    }
}
