package sphere;

import io.sphere.client.SphereResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.model.Review;
import io.sphere.client.shop.model.ReviewUpdate;
import play.libs.F.Promise;

/** Sphere HTTP API for working with product reviews in a given project.
 *
 * <p>For additional methods related to product comments and the currently authenticated customer,
 * see {@link Sphere#currentCustomer()}. */
public interface ReviewService {
    /** Finds a review by id. */
    FetchRequest<Review> byId(String id);

    /** Queries all reviews in current project.
     *
     * @deprecated since 0.49.0. Use {@link #query()} instead.
     **/
    @Deprecated
    QueryRequest<Review> all();

    /** Queries reviews in current project. */
    QueryRequest<Review> query();

    /** Queries all reviews for a specific product. */
    public QueryRequest<Review> byProductId(String productId);

    /** Updates a review. */
    public Review updateReview(VersionedId reviewId, ReviewUpdate update);

    /** Updates a review asynchronously. */
    public Promise<SphereResult<Review>> updateReviewAsync(VersionedId reviewId, ReviewUpdate update);
}
