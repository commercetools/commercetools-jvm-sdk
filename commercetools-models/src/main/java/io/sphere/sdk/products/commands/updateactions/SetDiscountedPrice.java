package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.productdiscounts.DiscountedPrice;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Discounts a product price. The referenced Product Discount in the discounted field must be active, valid, of type external and it’s predicate must match the referenced price.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setDiscountedPrice()}
 */
public final class SetDiscountedPrice extends StagedProductUpdateActionImpl<Product> {
    private final String priceId;
    @Nullable
    private final DiscountedPrice discounted;

    private SetDiscountedPrice(final String priceId, @Nullable final DiscountedPrice discounted, @Nullable final Boolean staged) {
        super("setDiscountedPrice", staged);
        this.priceId = priceId;
        this.discounted = discounted;
    }

    public String getPriceId() {
        return priceId;
    }

    @Nullable
    public DiscountedPrice getDiscounted() {
        return discounted;
    }

    public static SetDiscountedPrice of(final String priceId, @Nullable final DiscountedPrice discounted) {
        return of(priceId, discounted, null);
    }

    public static SetDiscountedPrice of(final String priceId, @Nullable final DiscountedPrice discounted, @Nullable final Boolean staged) {
        return new SetDiscountedPrice(priceId, discounted, staged);
    }
}
