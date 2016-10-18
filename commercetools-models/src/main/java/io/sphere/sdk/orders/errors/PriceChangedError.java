package io.sphere.sdk.orders.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;

import java.util.List;

/**
 * The price, tax or shipping of some line items changed since the items were added to the cart.
 *
 * {@include.example io.sphere.sdk.orders.errors.PriceChangedErrorIntegrationTest#order()}
 */
public final class PriceChangedError extends SphereError {
    public static final String CODE = "PriceChanged";

    private final List<String> lineItems;
    private final boolean shipping;

    @JsonCreator
    private PriceChangedError(final String message, final List<String> lineItems, final boolean shipping) {
        super(CODE, message);
        this.lineItems = lineItems;
        this.shipping = shipping;
    }

    public List<String> getLineItems() {
        return lineItems;
    }

    public boolean isShipping() {
        return shipping;
    }

    public static PriceChangedError of(final String message, final List<String> lineItems, final boolean shipping) {
        return new PriceChangedError(message, lineItems, shipping);
    }
}
