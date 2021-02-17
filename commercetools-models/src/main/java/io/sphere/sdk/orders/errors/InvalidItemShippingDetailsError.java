package io.sphere.sdk.orders.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;


public final class InvalidItemShippingDetailsError extends SphereError {
    public static final String CODE = "InvalidItemShippingDetails";

    private final String subject;
    private final String itemId;

    @JsonCreator
    private InvalidItemShippingDetailsError(final String message, final String subject, final String itemId) {
        super(CODE, message);
        this.subject = subject;
        this.itemId = itemId;
    }

    public static InvalidItemShippingDetailsError of(final String message, final String subject, final String itemId) {
        return new InvalidItemShippingDetailsError(message, subject, itemId);
    }

    public String getItemId() {
        return itemId;
    }

    public String getSubject() {
        return subject;
    }
}
