package io.sphere.sdk.reviews.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.MetaModelGetDsl;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.expansion.ReviewExpansionModel;

import java.util.List;

/**
 * Retrieves a review by a known ID.
 *
 * {@include.example io.sphere.sdk.reviews.queries.ReviewByIdGetTest#execution()}
 */
public interface ReviewByIdGet extends MetaModelGetDsl<Review, Review, ReviewByIdGet, ReviewExpansionModel<Review>> {
    static ReviewByIdGet of(final Identifiable<Review> productType) {
        return of(productType.getId());
    }

    static ReviewByIdGet of(final String id) {
        return new ReviewByIdGetImpl(id);
    }

    @Override
    List<ExpansionPath<Review>> expansionPaths();

    @Override
    ReviewByIdGet plusExpansionPaths(final ExpansionPath<Review> expansionPath);

    @Override
    ReviewByIdGet withExpansionPaths(final ExpansionPath<Review> expansionPath);

    @Override
    ReviewByIdGet withExpansionPaths(final List<ExpansionPath<Review>> expansionPaths);
}
