package io.sphere.sdk.orders.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;

/**
 * Error thrown during conversion of the cart into an order if the cart contains a discountCode with a DiscountCodeState different from MatchesCart.

 *
 * {@include.example io.sphere.sdk.orders.errors.DiscountCodeNonApplicableErrorIntegrationTest#order()}
 */
public final class DiscountCodeNonApplicableError extends SphereError {
    public static final String CODE = "DiscountCodeNonApplicable";

    @JsonCreator
    private DiscountCodeNonApplicableError(final String message) {
        super(CODE, message);
    }

    public static DiscountCodeNonApplicableError of(final String message) {
        return new DiscountCodeNonApplicableError(message);
    }
}
