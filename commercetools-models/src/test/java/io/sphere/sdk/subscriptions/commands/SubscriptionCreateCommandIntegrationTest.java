package io.sphere.sdk.subscriptions.commands;

import io.sphere.sdk.subscriptions.Subscription;
import io.sphere.sdk.subscriptions.SubscriptionDraftDsl;
import io.sphere.sdk.subscriptions.SubscriptionFixtures;
import io.sphere.sdk.subscriptions.queries.SubscriptionQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static io.sphere.sdk.subscriptions.SubscriptionFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link SubscriptionCreateCommand}.
 */
public class SubscriptionCreateCommandIntegrationTest extends IntegrationTest {

    @Before
    public void clean() {
        List<Subscription> results = client().executeBlocking(SubscriptionQuery.of()
                .withPredicates(l -> l.key().isIn(Arrays.asList(SubscriptionFixtures.IRON_MQ_SUBSCRIPTION_KEY, SubscriptionFixtures.AWS_SQS_SUBSCRIPTION_KEY))))
                .getResults();
        results.forEach(subscription -> client().executeBlocking(SubscriptionDeleteCommand.of(subscription)));
    }

    @Test
    public void createIronMqChangesSubscription() throws Exception {
        final SubscriptionDraftDsl subscriptionDraft = withCategoryChanges(ironMqSubscriptionDraftBuilder()).build();

        final SubscriptionCreateCommand createCommand = SubscriptionCreateCommand.of(subscriptionDraft);
        final Subscription subscription = client().executeBlocking(createCommand);

        assertThat(subscription).isNotNull();
        assertThat(subscription.getDestination()).isEqualTo(subscriptionDraft.getDestination());
        assertThat(subscription.getChanges()).isEqualTo(subscriptionDraft.getChanges());
    }

    @Test
    public void createIronMqMessagesSubscription() throws Exception {
        final SubscriptionDraftDsl subscriptionDraft = withCategoryCreatedMessage(ironMqSubscriptionDraftBuilder()).build();

        final SubscriptionCreateCommand createCommand = SubscriptionCreateCommand.of(subscriptionDraft);
        final Subscription subscription = client().executeBlocking(createCommand);

        assertThat(subscription).isNotNull();
        assertThat(subscription.getDestination()).isEqualTo(subscriptionDraft.getDestination());
        assertThat(subscription.getMessages()).isEqualTo(subscriptionDraft.getMessages());
    }

    @Test
    public void createSqsChangesSubscription() throws Exception {
        final SubscriptionDraftDsl subscriptionDraft = withCategoryChanges(sqsSubscriptionDraftBuilder()).build();

        final SubscriptionCreateCommand createCommand = SubscriptionCreateCommand.of(subscriptionDraft);
        final Subscription subscription = client().executeBlocking(createCommand);

        assertThat(subscription).isNotNull();
        assertThat(subscription.getDestination()).isEqualTo(subscriptionDraft.getDestination());
        assertThat(subscription.getChanges()).isEqualTo(subscriptionDraft.getChanges());
    }

    @Test
    public void createSnsChangesSubscription() throws Exception {
        final SubscriptionDraftDsl subscriptionDraft = withCategoryChanges(snsSubscriptionDraftBuilder()).build();

        final SubscriptionCreateCommand createCommand = SubscriptionCreateCommand.of(subscriptionDraft);
        final Subscription subscription = client().executeBlocking(createCommand);

        assertThat(subscription).isNotNull();
        assertThat(subscription.getDestination()).isEqualTo(subscriptionDraft.getDestination());
        assertThat(subscription.getChanges()).isEqualTo(subscriptionDraft.getChanges());
    }
}
