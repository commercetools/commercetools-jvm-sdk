package io.sphere.sdk.reviews.queries;

import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateDraft;
import io.sphere.sdk.states.StateType;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Locale;

import static io.sphere.sdk.channels.ChannelFixtures.withChannelOfRole;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.reviews.ReviewFixtures.*;
import static io.sphere.sdk.states.StateFixtures.withState;
import static io.sphere.sdk.states.StateRole.REVIEW_INCLUDED_IN_STATISTICS;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class ReviewQueryIntegrationTest extends IntegrationTest {
    @Test
    public void execution() {
        withCustomer(client(), customer -> {
            withState(client(), StateDraft.of(randomKey(), StateType.REVIEW_STATE).withRoles(REVIEW_INCLUDED_IN_STATISTICS), (State state) -> {
                withChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY, channel -> {
                    final String uniquenessValue = randomKey();
                    withReview(client(),
                            builder -> builder.uniquenessValue(uniquenessValue).target(channel).state(state).customer(customer),
                            review -> {
                                final ReviewQuery query = ReviewQuery.of()
                                        .plusPredicates(m -> m.key().is(review.getKey()))
                                        .plusPredicates(m -> m.id().is(review.getId()))
                                        .plusPredicates(m -> m.uniquenessValue().is(uniquenessValue))
                                        .plusPredicates(m -> m.locale().is(Locale.ENGLISH))
                                        .plusPredicates(m -> m.authorName().is(AUTHOR_NAME))
                                        .plusPredicates(m -> m.title().is(REVIEW_TITLE))
                                        .plusPredicates(m -> m.text().is(REVIEW_TEXT))
                                        .plusPredicates(m -> m.target().is(channel))
                                        .plusPredicates(m -> m.rating().is(100))
                                        .plusPredicates(m -> m.state().is(state))
                                        .plusPredicates(m -> m.includedInStatistics().is(true))
                                        .plusPredicates(m -> m.customer().is(customer))
                                        ;
                                final Review loadedReview = client().executeBlocking(query).head().get();
                                assertThat(loadedReview).isEqualTo(review);
                            });
                });
            });
        });
    }
}