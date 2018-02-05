package io.sphere.sdk.shippingmethods.errors;

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

    /**
     * @return Either {@code 'LineItem'} or {@code 'CustomLineItem'} to mark if it is about a line item or a custom line item.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @return The id of the (custom) line item where the quantities do not add up.
     */
    public String getItemId() {
        return itemId;
    }
}
