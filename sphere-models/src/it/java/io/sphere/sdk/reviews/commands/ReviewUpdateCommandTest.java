package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.ReviewFixtures;
import io.sphere.sdk.reviews.commands.updateactions.SetAuthorName;
import io.sphere.sdk.reviews.commands.updateactions.SetScore;
import io.sphere.sdk.reviews.commands.updateactions.SetText;
import io.sphere.sdk.reviews.commands.updateactions.SetTitle;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class ReviewUpdateCommandTest extends IntegrationTest {
    @Test
    public void setAuthorName() throws Exception {
        ReviewFixtures.withUpdateableReview(client(), review -> {
            final String newAuthorName = randomString();
            assertThat(review.getTitle()).isNotEqualTo(newAuthorName);

            final Review updatedReview = execute(ReviewUpdateCommand.of(review, SetAuthorName.of(newAuthorName)));
            assertThat(updatedReview.getAuthorName()).contains(newAuthorName);

            return updatedReview;
        });
    }

    @Test
    public void setScore() throws Exception {
        ReviewFixtures.withUpdateableReview(client(), review -> {
            final double newScore = 0.5;
            assertThat(Optional.ofNullable(review.getScore()).orElse(1.0)).isNotEqualTo(0.5);

            final Review updatedReview = execute(ReviewUpdateCommand.of(review, SetScore.of(newScore)));
            assertThat(updatedReview.getScore()).isEqualTo(newScore);

            return updatedReview;
        });
    }

    @Test
    public void setText() throws Exception {
        ReviewFixtures.withUpdateableReview(client(), review -> {
            final String newText = randomString();
            assertThat(review.getTitle()).isNotEqualTo(newText);

            final Review updatedReview = execute(ReviewUpdateCommand.of(review, SetText.of(newText)));
            assertThat(updatedReview.getText()).contains(newText);

            return updatedReview;
        });
    }
    
    @Test
    public void setTitle() throws Exception {
        ReviewFixtures.withUpdateableReview(client(), review -> {
            final String newTitle = randomString();
            assertThat(review.getTitle()).isNotEqualTo(newTitle);

            final Review updatedReview = execute(ReviewUpdateCommand.of(review, SetTitle.of(newTitle)));
            assertThat(updatedReview.getTitle()).contains(newTitle);

            return updatedReview;
        });
    }
}