package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Price;

/**
 *
 * {@include.example test.ProductCrudIntegrationTest#removePriceUpdateAction()}
 */
public class RemovePrice extends StageableProductUpdateAction {
    private final long variantId;
    private final Price price;

    private RemovePrice(final long variantId, final Price price, final boolean staged) {
        super("removePrice", staged);
        this.variantId = variantId;
        this.price = price;
    }

    public long getVariantId() {
        return variantId;
    }

    public Price getPrice() {
        return price;
    }

    public static RemovePrice of(final long variantId, final Price price, final boolean staged) {
        return new RemovePrice(variantId, price, staged);
    }

    public static RemovePrice of(final long variantId, final Price price) {
        return of(variantId, price, true);
    }
}
