package io.sphere.client.shop;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.QueryRequest;
import io.sphere.client.shop.model.*;

/** Sphere HTTP API for working with product reviews in a given project. */
public interface ReviewService {
    /** Finds a review by id. */
    FetchRequest<Review> byId(String id);

    /** Queries all reviews in current project. */
    QueryRequest<Review> all();

    /** Queries all reviews by given customer. */
    public QueryRequest<Review> byCustomerId(String customerId);

    /** Queries all reviews for a specific product by given customer. */
    public QueryRequest<Review> byCustomerIdProductId(String customerId, String productId);

    /** Queries all reviews for a specific product. */
    public QueryRequest<Review> byProductId(String productId);

    /** Creates a review. At least one of the three optional parameters (title, text, score) must be set. */
    public CommandRequest<Review> createReview(
            String productId, String customerId, String authorName, String title, String text, Double score);

    /** Updates a review. */
    public CommandRequest<Review> updateReview(String reviewId, int reviewVersion, ReviewUpdate update);

}
