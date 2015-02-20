package io.sphere.sdk.exceptions;

import io.sphere.sdk.models.Base;

public class SphereError extends Base {
    private final String code;
    private final String message;

    private SphereError(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static SphereError of(final String code, final String message) {
        return new SphereError(code, message);
    }
}
