package sphere.internal;

import io.sphere.client.SphereResult;
import io.sphere.client.model.VersionedId;
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

    @Deprecated
    @Override public QueryRequest<Review> all() {
        return Async.adapt(service.query());
    }

    @Override public QueryRequest<Review> query() {
        return Async.adapt(service.query());
    }

    @Override public QueryRequest<Review> byProductId(String productId) {
        return Async.adapt(service.forProduct(productId));
    }

    @Override public Review updateReview(VersionedId reviewId, ReviewUpdate update) {
        return Async.awaitResult(updateReviewAsync(reviewId, update));
    }

    @Override public Promise<SphereResult<Review>> updateReviewAsync(VersionedId reviewId, ReviewUpdate update) {
        return Async.execute(service.updateReview(reviewId, update));
    }
}
