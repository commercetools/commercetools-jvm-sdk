package io.sphere.sdk.products.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;


public final class SearchExecutionFailureError extends SphereError {
    public static final String CODE = "SearchExecutionFailure";

    @JsonCreator
    private SearchExecutionFailureError(final String message) {
        super(CODE, message);
    }

    public static SearchExecutionFailureError of(final String message) {
        return new SearchExecutionFailureError(message);
    }
}
