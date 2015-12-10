package io.sphere.sdk.reviews;

import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.reviews.commands.ReviewCreateCommand;
import io.sphere.sdk.reviews.commands.ReviewDeleteCommand;

import java.util.function.Function;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.products.ProductFixtures.withProduct;

public class ReviewFixtures {
    public static void withUpdateableReview(final TestClient client, final Function<Review, Review> function) {
        withProduct(client, product ->
            withCustomer(client, customer -> {
                final ReviewDraft reviewDraft = ReviewDraftBuilder.of(product, customer)
                        .authorName("John Smith")
                        .title("It is ok")
                        .text("This product is ok.")
                        .score(0.36)
                        .build();
                final Review review = client.executeBlocking(ReviewCreateCommand.of(reviewDraft));
                final Review updatedReview = function.apply(review);
                client.executeBlocking(ReviewDeleteCommand.of(updatedReview));
            })
        );
    }
}
