package io.sphere.sdk.reviews.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.states.State;
import io.sphere.sdk.types.queries.WithCustomQueryModel;

public interface ReviewQueryModel extends ResourceQueryModel<Review>,WithCustomQueryModel<Review> {
    StringQuerySortingModel<Review> key();

    StringQuerySortingModel<Review> uniquenessValue();

    StringQuerySortingModel<Review> title();

    StringQuerySortingModel<Review> text();

    StringQuerySortingModel<Review> authorName();

    LocaleQuerySortingModel<Review> locale();

    AnyReferenceQueryModel<Review> target();

    IntegerQuerySortingModel<Review> rating();

    ReferenceOptionalQueryModel<Review, State> state();

    BooleanQueryModel<Review> includedInStatistics();

    ReferenceOptionalQueryModel<Review, Customer> customer();

    static ReviewQueryModel of() {
        return new ReviewQueryModelImpl(null, null);
    }
}
