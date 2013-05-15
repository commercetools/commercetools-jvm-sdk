package sphere.internal;

import io.sphere.client.shop.model.Review;
import io.sphere.client.shop.model.ReviewUpdate;
import net.jcip.annotations.Immutable;
import play.libs.F.Promise;
import sphere.FetchRequest;
import sphere.QueryRequest;
import sphere.ReviewService;
import sphere.util.Async;

import javax.annotation.Nonnull;

/** ReviewService with Play-specific async methods. */
@Immutable
public class ReviewServiceAdapter implements ReviewService {
    private final io.sphere.client.shop.ReviewService service;
    public ReviewServiceAdapter(@Nonnull io.sphere.client.shop.ReviewService service) {
        if (service == null) throw new NullPointerException("service");
        this.service = service;
    }

    @Override public FetchRequest<Review> byId(String id) {
        return Async.adapt(service.byId(id));
    }

    @Override public QueryRequest<Review> all() {
        return Async.adapt(service.all());
    }

    @Override public QueryRequest<Review> byProductId(String productId) {
        return Async.adapt(service.byProductId(productId));
    }

    @Override public Review updateReview(String reviewId, int reviewVersion, ReviewUpdate update) {
        return Async.await(updateReviewAsync(reviewId, reviewVersion, update));
    }

    @Override public Promise<Review> updateReviewAsync(String reviewId, int reviewVersion, ReviewUpdate update) {
        return Async.execute(service.updateReview(reviewId, reviewVersion, update));
    }
}
