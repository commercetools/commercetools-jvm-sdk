package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.ProductUpdateScope;

/**
 *
 * {@include.example io.sphere.sdk.products.ProductCrudIntegrationTest#removePriceUpdateAction()}
 */
public class RemovePrice extends StageableProductUpdateAction {
    private final int variantId;
    private final Price price;

    private RemovePrice(final int variantId, final Price price, final ProductUpdateScope productUpdateScope) {
        super("removePrice", productUpdateScope);
        this.variantId = variantId;
        this.price = price;
    }

    public long getVariantId() {
        return variantId;
    }

    public Price getPrice() {
        return price;
    }

    public static RemovePrice of(final int variantId, final Price price, final ProductUpdateScope productUpdateScope) {
        return new RemovePrice(variantId, price, productUpdateScope);
    }
}
