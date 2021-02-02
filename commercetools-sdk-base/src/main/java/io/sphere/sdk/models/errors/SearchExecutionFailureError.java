package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;


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
