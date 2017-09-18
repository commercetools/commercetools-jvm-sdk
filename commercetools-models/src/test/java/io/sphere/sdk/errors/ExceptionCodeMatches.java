package io.sphere.sdk.errors;

import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.models.errors.SphereError;
import org.hamcrest.CustomTypeSafeMatcher;

public class ExceptionCodeMatches<T extends SphereError> extends CustomTypeSafeMatcher<ErrorResponseException> {
    private final Class<T> error;

    private ExceptionCodeMatches(final Class<T> error) {
        super("expects sphere error");
        this.error = error;
    }

    @Override
    protected boolean matchesSafely(final ErrorResponseException e) {
        boolean matches = false;
        if (!e.getErrors().isEmpty()) {
            final SphereError firstError = e.getErrors().get(0);
            try {
                firstError.as(error);
                matches = true;
            } catch (final IllegalArgumentException e1) {
                matches = false;
            }
        }
        return matches;
    }

    public static <T extends SphereError> ExceptionCodeMatches<T> of(final Class<T> error) {
        return new ExceptionCodeMatches<>(error);
    }
}