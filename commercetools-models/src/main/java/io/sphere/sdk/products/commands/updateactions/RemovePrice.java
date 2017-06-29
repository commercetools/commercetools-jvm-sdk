package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Removes a price from the product.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#removePrice()}
 */
public final class RemovePrice extends StagedProductUpdateActionImpl<Product> {
    private final String priceId;

    private RemovePrice(final String priceId, @Nullable final Boolean staged) {
        super("removePrice", staged);
        this.priceId = priceId;
    }

    public String getPriceId() {
        return priceId;
    }

    /**
     * Action to remove a price
     *
     * @param price the price to remove including an ID
     * @return action
     */
    public static RemovePrice of(final Price price) {
        return of(price, null);
    }

    public static RemovePrice of(final Price price, @Nullable final Boolean staged) {
        return of(Optional.ofNullable(price.getId()).orElseThrow(() -> new IllegalArgumentException("Expected price with ID.")), staged);
    }

    public static RemovePrice of(final String priceId) {
        return of(priceId, null);
    }

    public static RemovePrice of(final String priceId, @Nullable final Boolean staged) {
        return new RemovePrice(priceId, staged);
    }
}
