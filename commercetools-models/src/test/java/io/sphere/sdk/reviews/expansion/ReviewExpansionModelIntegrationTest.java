package io.sphere.sdk.reviews.expansion;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.ReviewDraft;
import io.sphere.sdk.reviews.ReviewDraftBuilder;
import io.sphere.sdk.reviews.commands.ReviewCreateCommand;
import io.sphere.sdk.reviews.commands.ReviewDeleteCommand;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateDraft;
import io.sphere.sdk.states.StateType;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.states.StateFixtures.withState;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class ReviewExpansionModelIntegrationTest extends IntegrationTest {
    @Test
    public void checkAll() {
            withCustomer(client(), (Customer customer) -> {
                withProduct(client(), (Product product) -> {
                    withState(client(), StateDraft.of(randomKey(), StateType.REVIEW_STATE), (State state) -> {
                        final ReviewDraft reviewDraft = ReviewDraftBuilder.ofRating(1)
                                .customer(customer)
                                .state(state)
                                .target(product)
                                .build();

                        final ReviewCreateCommand createCommand = ReviewCreateCommand.of(reviewDraft)
                                .plusExpansionPaths(m -> m.customer())
                                .plusExpansionPaths(m -> m.target())
                                .plusExpansionPaths(m -> m.state());
                        final Review review = client().executeBlocking(createCommand);

                        assertThat(review.getCustomer()).is(expanded());
                        assertThat(review.getTarget()).is(expanded());
                        assertThat(review.getState()).is(expanded());

                        client().executeBlocking(ReviewDeleteCommand.of(review));
                    });
                });
            });
    }
}