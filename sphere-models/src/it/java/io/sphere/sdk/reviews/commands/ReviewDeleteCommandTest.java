package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.ReviewDraft;
import io.sphere.sdk.reviews.ReviewDraftBuilder;
import io.sphere.sdk.reviews.queries.ReviewQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static org.assertj.core.api.Assertions.*;

public class ReviewDeleteCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withProduct(client(), product ->
                withCustomer(client(), customer -> {
                    final ReviewDraft reviewDraft = ReviewDraftBuilder.of(product, customer).score(0.5).build();
                    final Review review = execute(ReviewCreateCommand.of(reviewDraft));

                    execute(ReviewDeleteCommand.of(review));

                    PagedQueryResult<Review> result = execute(ReviewQuery.of().withPredicate(ReviewQuery.model().id().is(review.getId())));
                    assertThat(result.getTotal()).isEqualTo(0);
                })
        );
    }
}
