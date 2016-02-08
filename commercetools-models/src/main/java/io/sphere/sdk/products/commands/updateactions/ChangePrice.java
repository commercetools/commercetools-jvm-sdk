package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.products.Product;

import java.util.Optional;

/**
 * Replaces a price in the product variant's prices set. The price with the same price scope (same currency, country, customer group and channel) as the given price is replaced.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#changePrice()}
 */
public final class ChangePrice extends UpdateActionImpl<Product> {
    private final PriceDraft price;
    private final String priceId;

    private ChangePrice(final String priceId, final PriceDraft price) {
        super("changePrice");
        this.priceId = priceId;
        this.price = price;
    }

    public String getPriceId() {
        return priceId;
    }

    public PriceDraft getPrice() {
        return price;
    }

    public static ChangePrice of(final Price oldPrice, final PriceDraft price) {
        final String priceId = Optional.ofNullable(oldPrice.getId())
                .orElseThrow(() -> new IllegalArgumentException("The old price should have an ID: " + oldPrice));
        return of(priceId, price);
    }

    public static ChangePrice of(final String priceId, final PriceDraft price) {
        return new ChangePrice(priceId, price);
    }
}
