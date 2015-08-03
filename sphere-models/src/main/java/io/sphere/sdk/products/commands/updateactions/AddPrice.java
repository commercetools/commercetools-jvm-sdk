package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.Product;

/**
 * Adds the given price to the product variant's prices set. It is rejected if the product already contains a price with the same price scope (same currency, country, customer group and channel).
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#addPrice()}
 */
public class AddPrice extends UpdateActionImpl<Product> {
    private final Integer variantId;
    private final Price price;


    private AddPrice(final Integer variantId, final Price price) {
        super("addPrice");
        this.variantId = variantId;
        this.price = price;
    }

    public Integer getVariantId() {
        return variantId;
    }

    public Price getPrice() {
        return price;
    }

    public static AddPrice of(final Integer variantId, final Price price) {
        return new AddPrice(variantId, price);
    }
}
