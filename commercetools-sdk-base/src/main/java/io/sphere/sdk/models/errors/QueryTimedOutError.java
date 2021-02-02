package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;


public final class QueryTimedOutError extends SphereError {
    public static final String CODE = "QueryTimedOut";

    @JsonCreator
    private QueryTimedOutError(final String message) {
        super(CODE, message);
    }

    public static QueryTimedOutError of(final String message) {
        return new QueryTimedOutError(message);
    }
}
