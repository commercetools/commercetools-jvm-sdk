package io.sphere.sdk.reviews.queries;

import io.sphere.sdk.queries.ResourceQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQueryModel;
import io.sphere.sdk.reviews.Review;

public class ReviewQueryModel extends ResourceQueryModelImpl<Review> {
    private ReviewQueryModel(final QueryModel<Review> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public static ReviewQueryModel of() {
        return new ReviewQueryModel(null, null);
    }

    public StringQueryModel<Review> productId() {
        return stringModel("productId");
    }

    public StringQueryModel<Review> customerId() {
        return stringModel("customerId");
    }
}
