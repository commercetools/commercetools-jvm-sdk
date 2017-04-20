package io.sphere.sdk.subscriptions.commands;

import io.sphere.sdk.subscriptions.Subscription;
import io.sphere.sdk.subscriptions.SubscriptionFixtures;
import io.sphere.sdk.subscriptions.queries.SubscriptionQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Before;
import org.junit.Test;

import static io.sphere.sdk.subscriptions.SubscriptionFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link SubscriptionDeleteCommand}.
 */
public class SubscriptionDeleteCommandIntegrationTest extends IntegrationTest {

    @Before
    public void clean() {
        SubscriptionFixtures.deleteSubscription(client(), SubscriptionFixtures.IRON_MQ_SUBSCRIPTION_KEY);
    }

    @Test
    public void deleteByIdIronMq() throws Exception {
        assumeHasIronMqEnv();

        final Subscription subscription = createSubscription(client(), withCategoryChanges(ironMqSubscriptionDraftBuilder()));

        final SubscriptionDeleteCommand subscriptionDeleteCommand = SubscriptionDeleteCommand.ofKey(subscription.getKey(), subscription.getVersion());

        client().executeBlocking(subscriptionDeleteCommand);

        final SubscriptionQuery subscriptionQuery = SubscriptionQuery.of().withPredicates(m -> m.is(subscription));
        assertThat(client().executeBlocking(subscriptionQuery).head()).isEmpty();
    }

    @Test
    public void deleteByKeyIronMq() throws Exception {
        assumeHasIronMqEnv();

        final Subscription subscription = createSubscription(client(), withCategoryChanges(ironMqSubscriptionDraftBuilder()));

        final SubscriptionDeleteCommand subscriptionDeleteCommand = SubscriptionDeleteCommand.of(subscription);

        client().executeBlocking(subscriptionDeleteCommand);

        final SubscriptionQuery subscriptionQuery = SubscriptionQuery.of().withPredicates(m -> m.is(subscription));
        assertThat(client().executeBlocking(subscriptionQuery).head()).isEmpty();
    }
}
