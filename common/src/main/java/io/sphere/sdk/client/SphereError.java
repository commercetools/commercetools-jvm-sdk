package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

public class SphereError extends Base {
    private final String code;
    private final String message;

    public SphereError(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
