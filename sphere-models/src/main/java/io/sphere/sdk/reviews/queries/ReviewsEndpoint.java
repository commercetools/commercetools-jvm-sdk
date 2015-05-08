package io.sphere.sdk.reviews.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.reviews.Review;

final class ReviewsEndpoint {
    static final JsonEndpoint<Review> ENDPOINT = JsonEndpoint.of(Review.typeReference(), "/reviews");
}
