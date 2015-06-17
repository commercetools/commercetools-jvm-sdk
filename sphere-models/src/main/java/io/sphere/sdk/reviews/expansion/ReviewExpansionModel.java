package io.sphere.sdk.reviews.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.reviews.Review;

import java.util.Optional;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public class ReviewExpansionModel<T> extends ExpansionModel<T> {
    private ReviewExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    ReviewExpansionModel() {
        super();
    }

    public static ReviewExpansionModel<Review> of() {
        return new ReviewExpansionModel<>();
    }
}
