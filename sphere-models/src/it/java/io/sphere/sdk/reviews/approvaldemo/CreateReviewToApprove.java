package io.sphere.sdk.reviews.approvaldemo;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.ReviewDraft;
import io.sphere.sdk.reviews.ReviewDraftBuilder;
import io.sphere.sdk.reviews.commands.ReviewCreateCommand;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateReviewToApprove {
    public static Review createReview(final BlockingSphereClient client, final Reference<Product> productReference) {
        final ReviewDraft reviewDraft = ReviewDraftBuilder
                .ofRating(4)
                .title("review title")
                .target(productReference)
                .state(ResourceIdentifier.ofKey("to-approve"))//you know the initial state by key and don't need a ref
                .build();
        final Review review = client.executeBlocking(ReviewCreateCommand.of(reviewDraft));
        assertThat(review.getState()).isNotNull();
        assertThat(review.getRating()).isEqualTo(4);
        return review;
    }
}
