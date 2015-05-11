package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.ReviewDraft;
import io.sphere.sdk.reviews.ReviewDraftBuilder;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static org.assertj.core.api.Assertions.*;

public class ReviewCreateCommandTest extends IntegrationTest {

    @Test
    public void execution() throws Exception {
        withProduct(client(), product ->
            withCustomer(client(), customer -> {
                final ReviewDraft reviewDraft = ReviewDraftBuilder.of(product, customer)
                        .authorName("John Smith")
                        .title("It is ok")
                        .text("This product is ok.")
                        .score(0.36)
                        .build();
                final Review review = execute(ReviewCreateCommand.of(reviewDraft));
                assertThat(review.getAuthorName()).contains("John Smith");
                assertThat(review.getTitle()).contains("It is ok");
                assertThat(review.getText()).contains("This product is ok.");
                assertThat(review.getScore().get()).isEqualTo(0.36, within(0.1));
            })
        );
    }
}