package io.sphere.sdk.reviews.queries;

import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.expansion.ReviewExpansionModel;

final class ReviewByIdGetImpl extends MetaModelGetDslImpl<Review, Review, ReviewByIdGet, ReviewExpansionModel<Review>> implements ReviewByIdGet {
    ReviewByIdGetImpl(final String id) {
        super(id, ReviewEndpoint.ENDPOINT, ReviewExpansionModel.of(), ReviewByIdGetImpl::new);
    }

    public ReviewByIdGetImpl(final MetaModelGetDslBuilder<Review, Review, ReviewByIdGet, ReviewExpansionModel<Review>> builder) {
        super(builder);
    }
}
