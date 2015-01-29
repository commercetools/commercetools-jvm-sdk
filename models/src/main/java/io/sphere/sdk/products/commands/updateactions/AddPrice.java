package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.ProductUpdateScope;

/**
 * Adds the given price to the product variant's prices set. It is rejected if the product already contains a price with the same price scope (same currency, country, customer group and channel).
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#addPrice()}
 */
public class AddPrice extends StageableProductUpdateAction {
    private final int variantId;
    private final Price price;


    private AddPrice(final int variantId, final Price price, final ProductUpdateScope productUpdateScope) {
        super("addPrice", productUpdateScope);
        this.variantId = variantId;
        this.price = price;
    }

    public long getVariantId() {
        return variantId;
    }

    public Price getPrice() {
        return price;
    }

    public static AddPrice of(final int variantId, final Price price, final ProductUpdateScope productUpdateScope) {
        return new AddPrice(variantId, price, productUpdateScope);
    }
}
