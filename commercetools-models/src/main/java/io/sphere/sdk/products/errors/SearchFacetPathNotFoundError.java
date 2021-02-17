package io.sphere.sdk.products.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;


public final class SearchFacetPathNotFoundError extends SphereError {
    public static final String CODE = "SearchFacetPathNotFound";

    @JsonCreator
    private SearchFacetPathNotFoundError(final String message) {
        super(CODE, message);
    }

    public static SearchFacetPathNotFoundError of(final String message) {
        return new SearchFacetPathNotFoundError(message);
    }
}
