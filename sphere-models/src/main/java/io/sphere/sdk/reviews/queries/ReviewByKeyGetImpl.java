package io.sphere.sdk.reviews.queries;

import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.expansion.ReviewExpansionModel;

final class ReviewByKeyGetImpl extends MetaModelGetDslImpl<Review, Review, ReviewByKeyGet, ReviewExpansionModel<Review>> implements ReviewByKeyGet {
    ReviewByKeyGetImpl(final String key) {
        super("key=" + key, ReviewEndpoint.ENDPOINT, ReviewExpansionModel.of(), ReviewByKeyGetImpl::new);
    }

    public ReviewByKeyGetImpl(final MetaModelGetDslBuilder<Review, Review, ReviewByKeyGet, ReviewExpansionModel<Review>> builder) {
        super(builder);
    }
}
