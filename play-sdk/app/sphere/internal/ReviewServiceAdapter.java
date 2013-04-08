package sphere.internal;

import io.sphere.client.shop.model.Review;
import net.jcip.annotations.Immutable;
import sphere.CommandRequest;
import sphere.FetchRequest;
import sphere.QueryRequest;
import sphere.ReviewService;

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
        return new FetchRequestAdapter<Review>(service.byId(id));
    }

    @Override public QueryRequest<Review> all() {
        return new QueryRequestAdapter<Review>(service.all());
    }

    @Override public QueryRequest<Review> byProductId(String productId) {
        return new QueryRequestAdapter<Review>(service.byProductId(productId));
    }

    @Override public CommandRequest<Review> updateReview(String reviewId, int reviewVersion, String title, String text, Double score) {
        return new CommandRequestAdapter<Review>(service.updateReview(reviewId, reviewVersion, title, text, score));
    }
}
