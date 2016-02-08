package io.sphere.sdk.reviews.queries;

import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.expansion.ReviewExpansionModel;


final class ReviewQueryImpl extends MetaModelQueryDslImpl<Review, ReviewQuery, ReviewQueryModel, ReviewExpansionModel<Review>> implements ReviewQuery {
    ReviewQueryImpl(){
        super(ReviewEndpoint.ENDPOINT.endpoint(), ReviewQuery.resultTypeReference(), ReviewQueryModel.of(), ReviewExpansionModel.of(), ReviewQueryImpl::new);
    }

    private ReviewQueryImpl(final MetaModelQueryDslBuilder<Review, ReviewQuery, ReviewQueryModel, ReviewExpansionModel<Review>> builder) {
        super(builder);
    }
}