package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.ReviewDraft;
import io.sphere.sdk.reviews.ReviewFixtures;
import io.sphere.sdk.reviews.queries.ReviewByIdGet;
import io.sphere.sdk.test.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ReviewDeleteCommandIntegrationTest extends IntegrationTest {

    @Test
    public void deleteWithDataErasure(){
        final ReviewDraft draft =  ReviewFixtures.createReviewDraftBuilder().build();
        final Review review = client().executeBlocking(ReviewCreateCommand.of(draft));
        client().executeBlocking(ReviewDeleteCommand.of(review,true));
        Review queriedReview = client().executeBlocking(ReviewByIdGet.of(review));
        Assertions.assertThat(queriedReview).isNull();
    }

}
