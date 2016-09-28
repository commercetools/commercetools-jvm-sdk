package io.sphere.sdk.orders.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;

import java.util.List;

/**
 *Some of the ordered line items are out of stock at the time of placing the order.
 *
 * {@include.example io.sphere.sdk.orders.errors.OutOfStockErrorIntegrationTest#order()}
 */
public final class OutOfStockError extends SphereError {
    public static final String CODE = "OutOfStock";

    private final List<String> lineItems;
    private final List<String> skus;

    @JsonCreator
    private OutOfStockError(final String message, final List<String> lineItems, final List<String> skus) {
        super(CODE, message);
        this.lineItems = lineItems;
        this.skus = skus;
    }

    public List<String> getLineItems() {
        return lineItems;
    }

    public List<String> getSkus() {
        return skus;
    }

    public static OutOfStockError of(final String message, final List<String> lineItems, final List<String> skus) {
        return new OutOfStockError(message, lineItems, skus);
    }
}
