package io.sphere.sdk.reviews.approvaldemo;

import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.commands.ReviewDeleteCommand;
import io.sphere.sdk.reviews.queries.ReviewQuery;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.commands.StateDeleteCommand;
import io.sphere.sdk.states.queries.StateQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static java.util.Arrays.asList;

public class ReviewApprovalIntegrationTest extends IntegrationTest {

    @BeforeClass
    public static void clean() {
        client().executeBlocking(StateQuery.of()
                .withPredicates(m -> m.key().isIn(asList("approved", "to-approve")))
                .withSort(m -> m.key().sort().desc()))
                .getResults()
                .forEach(state -> {
                    final ReviewQuery reviewQuery = ReviewQuery.of()
                            .withPredicates(m -> m.state().is(state))
                            .withLimit(500);
                    client().executeBlocking(reviewQuery)
                            .getResults()
                            .forEach(review -> client().executeBlocking(ReviewDeleteCommand.of(review)));
                    client().executeBlocking(StateDeleteCommand.of(state));
                });
    }

    @Test
    public void executeScenario() {
        final List<State> states = CreateReviewStates.createStates(client());
        final State initial = states.get(0);
        final State approved = states.get(1);
        withProduct(client(), product -> {
            final Review review = CreateReviewToApprove.createReview(client(), product.toReference());
            QueryReviewsToApprove.query(client(), initial);
            final Review approvedReview = ApprovingAReview.approveReview(client(), review);
            client().executeBlocking(ReviewDeleteCommand.of(approvedReview));
            asList(initial, approved).forEach(state -> client().executeBlocking(StateDeleteCommand.of(state)));
        });
    }
}