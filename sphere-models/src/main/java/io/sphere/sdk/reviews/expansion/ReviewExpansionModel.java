package io.sphere.sdk.reviews.expansion;

import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.states.expansion.StateExpansionModel;

/**
 DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public interface ReviewExpansionModel<T> {
    CustomerExpansionModel<T> customer();

    StateExpansionModel<T> state();

    ExpansionPathContainer<T> target();

    static ReviewExpansionModel<Review> of() {
        return new ReviewExpansionModelImpl<>();
    }
}
