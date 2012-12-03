package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.CommandRequest;
import de.commercetools.sphere.client.FetchRequest;
import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.shop.model.*;

/** Sphere HTTP API for working with reviews in a given project. */
public interface ReviewService {
    /** Creates a request that finds a review by given id. */
    FetchRequest<Review> byId(String id);

    /** Creates a request that queries all reviews. */
    QueryRequest<Review> all();

    /** Creates a request builder that queries all reviews of the given customer. */
    public QueryRequest<Review> byCustomerId(String customerId);

    /** Creates a request builder that queries all reviews of the given product. */
    public QueryRequest<Review> byProductId(String productId);

    /** Creates a review. At least one of the three optional parameters (title, text, score) must be set. */
    public CommandRequest<Review> createReview(String productId,
                                               String customerId,
                                               String authorName,
                                               String title,
                                               String text,
                                               Double score);

    /** Updates a review. At least one of the three optional parameters (title, text, score) must be set.
     *  Unset value (null) will delete the field. */
    public CommandRequest<Review> updateReview(String reviewId, int reviewVersion, String title, String text, Double score);
}