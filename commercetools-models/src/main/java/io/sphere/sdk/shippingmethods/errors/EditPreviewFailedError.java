package io.sphere.sdk.shippingmethods.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;
import io.sphere.sdk.orderedits.OrderEditPreviewFailure;


public final class EditPreviewFailedError extends SphereError {

    public static final String CODE = "EditPreviewFailed";

    private final OrderEditPreviewFailure result;

    @JsonCreator
    private EditPreviewFailedError(final String message, final OrderEditPreviewFailure result) {
        super(CODE, message);
        this.result = result;
    }

    public static EditPreviewFailedError of(final String message, final OrderEditPreviewFailure result) {
        return new EditPreviewFailedError(message, result);
    }

    public OrderEditPreviewFailure getResult() {
        return result;
    }
}
