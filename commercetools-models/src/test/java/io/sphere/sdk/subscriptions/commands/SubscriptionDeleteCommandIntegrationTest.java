package io.sphere.sdk.subscriptions.commands;

import io.sphere.sdk.subscriptions.Subscription;
import io.sphere.sdk.subscriptions.SubscriptionFixtures;
import io.sphere.sdk.subscriptions.queries.SubscriptionQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.subscriptions.SubscriptionFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link SubscriptionDeleteCommand}.
 */
public class SubscriptionDeleteCommandIntegrationTest extends IntegrationTest {

    @BeforeClass
    public static void clean() {
        List<Subscription> results = client().executeBlocking(SubscriptionQuery.of()
                .withPredicates(l -> l.key().is(SubscriptionFixtures.IRON_MQ_SUBSCRIPTION_KEY)))
                .getResults();
        results.forEach(subscription -> client().executeBlocking(SubscriptionDeleteCommand.of(subscription)));
    }

    @Test
    public void deleteByIdIronMq() throws Exception {
        final Subscription subscription = createSubscription(client(), withCategoryChanges(ironMqSubscriptionDraftBuilder()));

        final SubscriptionDeleteCommand subscriptionDeleteCommand = SubscriptionDeleteCommand.ofKey(subscription.getKey(), subscription.getVersion());

        client().executeBlocking(subscriptionDeleteCommand);

        final SubscriptionQuery subscriptionQuery = SubscriptionQuery.of().withPredicates(m -> m.is(subscription));
        assertThat(client().executeBlocking(subscriptionQuery).head()).isEmpty();
    }

    @Test
    public void deleteByKeyIronMq() throws Exception {
        final Subscription subscription = createSubscription(client(), withCategoryChanges(ironMqSubscriptionDraftBuilder()));

        final SubscriptionDeleteCommand subscriptionDeleteCommand = SubscriptionDeleteCommand.of(subscription);

        client().executeBlocking(subscriptionDeleteCommand);

        final SubscriptionQuery subscriptionQuery = SubscriptionQuery.of().withPredicates(m -> m.is(subscription));
        assertThat(client().executeBlocking(subscriptionQuery).head()).isEmpty();
    }
}
