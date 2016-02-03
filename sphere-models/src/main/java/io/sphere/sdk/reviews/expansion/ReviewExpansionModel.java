package io.sphere.sdk.reviews.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.reviews.Review;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public final class ReviewExpansionModel<T> extends ExpansionModel<T> {
    public ReviewExpansionModel(final String parentPath, final String path) {
        super(parentPath, path);
    }

    ReviewExpansionModel() {
        super();
    }

    public static ReviewExpansionModel<Review> of() {
        return new ReviewExpansionModel<>();
    }

    public ExpansionPathContainer<T> customer() {
        return expansionPath("customer");
    }

    public ExpansionPathContainer<T> state() {
        return expansionPath("state");
    }

    public ExpansionPathContainer<T> target() {
        return expansionPath("target");
    }
}
