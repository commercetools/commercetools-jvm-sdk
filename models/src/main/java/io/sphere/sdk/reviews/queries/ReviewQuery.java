package io.sphere.sdk.reviews.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryDsl;
import io.sphere.sdk.reviews.Review;

/**
 {@doc.gen summary reviews}
 */
public class ReviewQuery extends DefaultModelQuery<Review> {
    private static final TypeReference<PagedQueryResult<Review>> RESULT_TYPE_REFERENCE = new TypeReference<PagedQueryResult<Review>>(){
        @Override
        public String toString() {
            return "TypeReference<PagedQueryResult<Review>>";
        }
    };

    private ReviewQuery() {
        super(ReviewsEndpoint.ENDPOINT.endpoint(), resultTypeReference());
    }

    public static TypeReference<PagedQueryResult<Review>> resultTypeReference() {
        return RESULT_TYPE_REFERENCE;
    }

    public QueryDsl<Review> byId(final String id) {
        return withPredicate(model().id().is(id));
    }

    public QueryDsl<Review> byProductId(final String productId) {
        return withPredicate(model().productId().is(productId));
    }

    public static ReviewQueryModel model() {
        return ReviewQueryModel.get();
    }

    public static ReviewQuery of() {
        return new ReviewQuery();
    }
}
