package io.sphere.sdk.subscriptions.commands;

import io.sphere.sdk.subscriptions.Subscription;
import io.sphere.sdk.subscriptions.queries.SubscriptionQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.subscriptions.SubscriptionFixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link SubscriptionDeleteCommand}.
 */
public class SubscriptionDeleteCommandIntegrationTest extends IntegrationTest {

    @Test
    public void deleteByKeyIronMq() throws Exception {
        assumeHasIronMqEnv();

        final Subscription subscription = createSubscription(client(), withCategoryChanges(ironMqSubscriptionDraftBuilder()));

        final SubscriptionDeleteCommand subscriptionDeleteCommand = SubscriptionDeleteCommand.ofKey(subscription.getKey(), subscription.getVersion());

        client().executeBlocking(subscriptionDeleteCommand);

        assertEventually(() -> {
            final SubscriptionQuery subscriptionQuery = SubscriptionQuery.of().withPredicates(m -> m.is(subscription));
            assertThat(client().executeBlocking(subscriptionQuery).head()).isEmpty();
        });
    }

    @Test
    public void deleteByIdIronMq() throws Exception {
        assumeHasIronMqEnv();

        final Subscription subscription = createSubscription(client(), withCategoryChanges(ironMqSubscriptionDraftBuilder()));

        final SubscriptionDeleteCommand subscriptionDeleteCommand = SubscriptionDeleteCommand.of(subscription);

        client().executeBlocking(subscriptionDeleteCommand);

        assertEventually(() -> {
            final SubscriptionQuery subscriptionQuery = SubscriptionQuery.of().withPredicates(m -> m.is(subscription));
            assertThat(client().executeBlocking(subscriptionQuery).head()).isEmpty();
        });
    }
}
