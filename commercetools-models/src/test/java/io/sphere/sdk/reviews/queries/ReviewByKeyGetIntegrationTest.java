package io.sphere.sdk.reviews.queries;

import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.reviews.ReviewFixtures.withReview;
import static org.assertj.core.api.Assertions.assertThat;

public class ReviewByKeyGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() {
        withReview(client(), (Review review) -> {
            final Review loadedReview = client().executeBlocking(ReviewByIdGet.of(review.getId()));
            assertThat(loadedReview.getId()).isEqualTo(review.getId());
        });
    }
}