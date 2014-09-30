package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Price;

/**
 * Adds the given price to the product variant's prices set. It is rejected if the product already contains a price with the same price scope (same currency, country, customer group and channel).
 *
 * {@include.example io.sphere.sdk.products.ProductCrudIntegrationTest#addPriceUpdateAction()}
 */
public class AddPrice extends StageableProductUpdateAction {
    private final long variantId;
    private final Price price;


    private AddPrice(final long variantId, final Price price, final boolean staged) {
        super("addPrice", staged);
        this.variantId = variantId;
        this.price = price;
    }

    public long getVariantId() {
        return variantId;
    }

    public Price getPrice() {
        return price;
    }

    public static AddPrice of(final long variantId, final Price price, final boolean staged) {
        return new AddPrice(variantId, price, staged);
    }

    public static AddPrice of(final long variantId, final Price price) {
        return of(variantId, price, true);
    }
}
