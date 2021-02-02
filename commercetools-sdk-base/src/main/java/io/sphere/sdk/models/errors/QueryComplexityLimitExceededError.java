package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;


public final class QueryComplexityLimitExceededError extends SphereError {
    public static final String CODE = "QueryComplexityLimitExceeded";

    @JsonCreator
    private QueryComplexityLimitExceededError(final String message) {
        super(CODE, message);
    }

    public static QueryComplexityLimitExceededError of(final String message) {
        return new QueryComplexityLimitExceededError(message);
    }
}
