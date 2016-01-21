package io.sphere.sdk.reviews.approvaldemo;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.commands.ReviewUpdateCommand;
import io.sphere.sdk.reviews.commands.updateactions.TransitionState;
import io.sphere.sdk.states.State;

import static org.assertj.core.api.Assertions.assertThat;


public class ApprovingAReview {
    public static Review approveReview(final BlockingSphereClient client, final Review reviewToApprove) {
        final ResourceIdentifier<State> state = ResourceIdentifier.ofKey("approved");//we know the state by key
        final ReviewUpdateCommand cmd = ReviewUpdateCommand.of(reviewToApprove, TransitionState.of(state));
        final Review approvedReview = client.executeBlocking(cmd);
        assertThat(reviewToApprove.getState()).isNotEqualTo(approvedReview.getState());
        return approvedReview;
    }
}
