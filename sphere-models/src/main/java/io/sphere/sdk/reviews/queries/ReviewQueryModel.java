package io.sphere.sdk.reviews.queries;

import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.reviews.Review;

import java.util.Optional;

public class ReviewQueryModel<T> extends DefaultModelQueryModelImpl<T> {
    private ReviewQueryModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public static ReviewQueryModel<Review> of() {
        return new ReviewQueryModel<>(Optional.<QueryModel<Review>>empty(), Optional.<String>empty());
    }

    public StringQueryModel<T> productId() {
        return new StringQuerySortingModel<>(Optional.of(this), "productId");
    }

    public StringQueryModel<T> customerId() {
        return new StringQuerySortingModel<>(Optional.of(this), "customerId");
    }

    //TODO more fields
}
