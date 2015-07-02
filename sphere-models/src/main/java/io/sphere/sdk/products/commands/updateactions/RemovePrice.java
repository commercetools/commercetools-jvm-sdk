package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.ProductUpdateScope;

/**
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#removePrice()}
 */
public class RemovePrice extends StageableProductUpdateAction {
    private final String priceId;

    private RemovePrice(final String priceId, final ProductUpdateScope productUpdateScope) {
        super("removePrice", productUpdateScope);
        this.priceId = priceId;
    }

    public String getPriceId() {
        return priceId;
    }

    /**
     * Action to remove a price
     * @param price the price to remove including an ID
     * @param productUpdateScope scope for the product update
     * @return action
     */
    public static RemovePrice of(final Price price, final ProductUpdateScope productUpdateScope) {
        return of(price.getId().orElseThrow(() -> new IllegalArgumentException("Expected price with ID.")), productUpdateScope);
    }

    public static RemovePrice of(final String priceId, final ProductUpdateScope productUpdateScope) {
        return new RemovePrice(priceId, productUpdateScope);
    }
}
