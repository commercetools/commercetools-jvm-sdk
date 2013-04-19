package sphere;

import io.sphere.client.shop.model.Review;
import io.sphere.client.shop.model.ReviewUpdate;

/** Sphere HTTP API for working with product reviews in a given project.
 *
 * <p>For additional methods related to product comments and the currently authenticated customer,
 * see {@link sphere.SphereClient#currentCustomer()}. */
public interface ReviewService {
    /** Finds a review by id. */
    FetchRequest<Review> byId(String id);

    /** Queries all reviews in current project. */
    QueryRequest<Review> all();

    /** Queries all reviews for a specific product. */
    public QueryRequest<Review> byProductId(String productId);

    /** Updates a review. */
    public CommandRequest<Review> updateReview(String reviewId, int reviewVersion, ReviewUpdate update);
}
