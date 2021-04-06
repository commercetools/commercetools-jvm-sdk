package io.sphere.sdk.products.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;


public final class SearchDeactivatedError extends SphereError {
    public static final String CODE = "SearchDeactivated";

    @JsonCreator
    private SearchDeactivatedError(final String message) {
        super(CODE, message);
    }

    public static SearchDeactivatedError of(final String message) {
        return new SearchDeactivatedError(message);
    }
}
