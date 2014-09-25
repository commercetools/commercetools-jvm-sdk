package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Price;

/**
 * Replaces a price in the product variant's prices set. The price with the same price scope (same currency, country, customer group and channel) as the given price is replaced.
 *
 * {@include.example io.sphere.sdk.products.ProductCrudIntegrationTest#changePriceUpdateAction()}
 */
public class ChangePrice extends StageableProductUpdateAction {
    private final long variantId;
    private final Price price;

    public ChangePrice(final long variantId, final Price price, final boolean staged) {
        super("changePrice", staged);
        this.variantId = variantId;
        this.price = price;
    }

    public long getVariantId() {
        return variantId;
    }

    public Price getPrice() {
        return price;
    }

    public static ChangePrice of(final long variantId, final Price price, final boolean staged) {
        return new ChangePrice(variantId, price, staged);
    }

    public static ChangePrice of(final long variantId, final Price price) {
        return of(variantId, price, true);
    }
}
