package io.sphere.internal;

import javax.annotation.Nonnull;

/** Individual error as part of {@link SphereErrorResponse}. */
public class SphereError {
    @Nonnull private String code;
    private String message;

    // for JSON deserializer
    private SphereError() {}

    /** The error code, such as 'InvalidOperation'. */
    @Nonnull public String getCode() { return code; }

    /** The error message. */
    public String getMessage() { return message; }

    @Override public String toString() {
        return getCode() + ": " + getMessage();
    }
}
