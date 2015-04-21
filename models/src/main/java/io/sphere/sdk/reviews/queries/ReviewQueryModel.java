package io.sphere.sdk.reviews.queries;

import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.reviews.Review;

import java.util.Optional;

public class ReviewQueryModel extends DefaultModelQueryModelImpl<Review> {
    private ReviewQueryModel(final Optional<? extends QueryModel<Review>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    static ReviewQueryModel get() {
        return new ReviewQueryModel(Optional.<QueryModel<Review>>empty(), Optional.<String>empty());
    }

    public StringQueryModel<Review> productId() {
        return new StringQuerySortingModel<>(Optional.of(this), "productId");
    }
}
