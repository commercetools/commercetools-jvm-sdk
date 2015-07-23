package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.ProductUpdateScope;

import java.util.Optional;

/**
 * Replaces a price in the product variant's prices set. The price with the same price scope (same currency, country, customer group and channel) as the given price is replaced.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#changePrice()}
 */
public class ChangePrice extends StageableProductUpdateAction {
    private final Price price;
    private final String priceId;

    private ChangePrice(final String priceId, final Price price, final ProductUpdateScope productUpdateScope) {
        super("changePrice", productUpdateScope);
        this.priceId = priceId;
        this.price = price;
    }

    public String getPriceId() {
        return priceId;
    }

    public Price getPrice() {
        return price;
    }

    public static ChangePrice of(final Price oldPrice, final Price price, final ProductUpdateScope productUpdateScope) {
        final String priceId = Optional.ofNullable(oldPrice.getId())
                .orElseThrow(() -> new IllegalArgumentException("The old price should have an ID: " + oldPrice));
        return of(priceId, price, productUpdateScope);
    }

    public static ChangePrice of(final String priceId, final Price price, final ProductUpdateScope productUpdateScope) {
        return new ChangePrice(priceId, price, productUpdateScope);
    }
}
