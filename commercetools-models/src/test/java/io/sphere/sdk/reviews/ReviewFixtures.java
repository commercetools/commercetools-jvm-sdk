package io.sphere.sdk.reviews;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.reviews.commands.ReviewCreateCommand;
import io.sphere.sdk.reviews.commands.ReviewDeleteCommand;
import io.sphere.sdk.reviews.queries.ReviewQuery;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static io.sphere.sdk.test.SphereTestUtils.randomKey;

public class ReviewFixtures {

    public static final String REVIEW_TITLE = "A review title";
    public static final String REVIEW_TEXT = "It is great.";
    public static final String AUTHOR_NAME = "John Smith";

    public static void withReview(final BlockingSphereClient client, final UnaryOperator<ReviewDraftBuilder> builderUnaryOperator, final Consumer<Review> consumer) {
        final ReviewDraft draft = builderUnaryOperator.apply(createReviewDraftBuilder()).build();
        withReview(client, draft, consumer);
    }

    public static ReviewDraftBuilder createReviewDraftBuilder() {
        return ReviewDraftBuilder.ofTitle(REVIEW_TITLE)
                .authorName(AUTHOR_NAME)
                .text(REVIEW_TEXT)
                .rating(100)
                .locale(Locale.ENGLISH)
                .key(randomKey());
    }

    public static void withReview(final BlockingSphereClient client, final Consumer<Review> consumer) {
        withReview(client, createReviewDraftBuilder().build(), consumer);
    }

    public static void withUpdateableReview(final BlockingSphereClient client, final UnaryOperator<ReviewDraftBuilder> builderUnaryOperator, final UnaryOperator<Review> f) {
        final ReviewDraft draft = builderUnaryOperator.apply(createReviewDraftBuilder()).build();
        final Review review = client.executeBlocking(ReviewCreateCommand.of(draft));
        final Review reviewToDelete = f.apply(review);
        client.executeBlocking(ReviewDeleteCommand.of(reviewToDelete));
    }

    public static void withUpdateableReview(final BlockingSphereClient client, final UnaryOperator<Review> f) {
        withUpdateableReview(client, UnaryOperator.identity(), f);
    }

    public static void withReview(final BlockingSphereClient client, final ReviewDraft draft, final Consumer<Review> consumer) {
        final Review review = client.executeBlocking(ReviewCreateCommand.of(draft));
        consumer.accept(review);
        client.executeBlocking(ReviewDeleteCommand.of(review));
    }

    public static void deleteReviews(final BlockingSphereClient client) {
        do {
            client.executeBlocking(ReviewQuery.of().withLimit(500))
                    .getResults()
                    .forEach(r -> client.executeBlocking(ReviewDeleteCommand.of(r)));
        } while (!client.executeBlocking(ReviewQuery.of().withLimit(1)).getResults().isEmpty());
    }
}
