package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;


public final class EditPreviewFailedError extends SphereError {

    public static final String CODE = "EditPreviewFailed";

    private final String result;

    @JsonCreator
    private EditPreviewFailedError(final String message, final String result) {
        super(CODE, message);
        this.result = result;
    }

    public static EditPreviewFailedError of(final String message, final String result) {
        return new EditPreviewFailedError(message, result);
    }

    public String getResult() {
        return result;
    }
}
