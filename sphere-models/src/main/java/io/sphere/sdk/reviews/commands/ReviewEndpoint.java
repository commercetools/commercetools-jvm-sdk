package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.reviews.Review;

final class ReviewEndpoint {
    static final JsonEndpoint<Review> ENDPOINT = JsonEndpoint.of(Review.typeReference(), "/reviews");
}
