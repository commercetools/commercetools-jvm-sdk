package io.sphere.sdk.reviews.approvaldemo;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.queries.ReviewQuery;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.queries.StateQuery;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryReviewsToApprove {
    public static List<Review> query(final BlockingSphereClient client, final State initialState) {
        //this ID can be cached
        final String initialStateId = client.executeBlocking(StateQuery.of().byKey("to-approve")).head().get().getId();

        final ReviewQuery reviewQuery = ReviewQuery.of()
                .withPredicates(m -> m.state().id().is(initialStateId))
                .withSort(m -> m.createdAt().sort().asc());//oldest first
        final List<Review> reviewsToApprove = client.executeBlocking(reviewQuery).getResults();
        assertThat(reviewsToApprove).extracting(review -> review.getTitle()).contains("review title");
        return reviewsToApprove;
    }
}
