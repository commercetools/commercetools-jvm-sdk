package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.attributes.AttributeDraft;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.ProductUpdateScope;

import java.util.List;
import java.util.Optional;

/**
 * Adds a variant to a product.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#addVariant()}
 *
 * @see io.sphere.sdk.products.commands.updateactions.RemoveVariant
 */
public class AddVariant extends StageableProductUpdateAction {
    private final Optional<String> sku;
    private final List<Price> prices;
    private final List<AttributeDraft> attributes;

    private AddVariant(final List<AttributeDraft> attributes, final List<Price> prices, final Optional<String> sku, final ProductUpdateScope productUpdateScope) {
        super("addVariant", productUpdateScope);
        this.attributes = attributes;
        this.prices = prices;
        this.sku = sku;
    }

    public List<AttributeDraft> getAttributes() {
        return attributes;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public Optional<String> getSku() {
        return sku;
    }

    public static AddVariant of(final List<AttributeDraft> attributes, final List<Price> prices, final Optional<String> sku, final ProductUpdateScope productUpdateScope) {
        return new AddVariant(attributes, prices, sku, productUpdateScope);
    }
}
