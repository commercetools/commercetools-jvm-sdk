package io.sphere.sdk.reviews.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.expansion.ReviewExpansionModel;

import java.util.List;

/**
 * Retrieves a review by a known key.
 *
 * {@include.example }
 */
public interface ReviewByKeyGet extends MetaModelGetDsl<Review, Review, ReviewByKeyGet, ReviewExpansionModel<Review>> {
    static ReviewByKeyGet of(final String key) {
        return new ReviewByKeyGetImpl(key);
    }

    @Override
    List<ExpansionPath<Review>> expansionPaths();

    @Override
    ReviewByKeyGet plusExpansionPaths(final ExpansionPath<Review> expansionPath);

    @Override
    ReviewByKeyGet withExpansionPaths(final ExpansionPath<Review> expansionPath);

    @Override
    ReviewByKeyGet withExpansionPaths(final List<ExpansionPath<Review>> expansionPaths);
}
