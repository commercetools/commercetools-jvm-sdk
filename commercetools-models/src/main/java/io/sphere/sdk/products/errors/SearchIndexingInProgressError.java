package io.sphere.sdk.products.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;


public final class SearchIndexingInProgressError extends SphereError {
    public static final String CODE = "SearchIndexingInProgress";

    @JsonCreator
    private SearchIndexingInProgressError(final String message) {
        super(CODE, message);
    }

    public static SearchIndexingInProgressError of(final String message) {
        return new SearchIndexingInProgressError(message);
    }
}
