package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductVariant;

/**
 * Adds the given price to the product variant's prices set. It is rejected if the product already contains a price with the same price scope (same currency, country, customer group and channel).
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#addPrice()}
 *
 * @see ProductVariant#getPrices()
 */
public class AddPrice extends UpdateActionImpl<Product> {
    private final Integer variantId;
    private final PriceDraft price;


    private AddPrice(final Integer variantId, final PriceDraft price) {
        super("addPrice");
        this.variantId = variantId;
        this.price = price;
    }

    public Integer getVariantId() {
        return variantId;
    }

    public PriceDraft getPrice() {
        return price;
    }

    public static AddPrice of(final Integer variantId, final PriceDraft price) {
        return new AddPrice(variantId, price);
    }
}
